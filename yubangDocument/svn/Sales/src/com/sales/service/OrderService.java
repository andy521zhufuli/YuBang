package com.sales.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.CommonDefs;
import com.sales.common.until.ReturnCode;
import com.sales.dao.impl.TCancelOrderDAOImpl;
import com.sales.dao.impl.TGoodsDAOImpl;
import com.sales.dao.impl.TOrderDAOImpl;
import com.sales.dao.impl.TOrderHistDAOImpl;
import com.sales.dao.impl.TUsrDAOImpl;
import com.sales.exception.SalesLogicalException;
import com.sales.model.TCancelOrder;
import com.sales.model.TGoods;
import com.sales.model.TOrder;
import com.sales.model.TOrderHist;
import com.sales.model.TUsr;
import com.sales.vo.GetHistOrdersReq;
import com.sales.vo.GetHistOrdersResp;
import com.sales.vo.ModifyOrderReq;
import com.sales.vo.base.AddressInfo;
import com.sales.vo.base.MyOrder;
import com.sales.vo.base.Order;
import com.sales.vo.base.OrderCancelReason;
import com.sales.vo.base.OrderGoods;

@Service
public class OrderService {
	final private static JLogger logger = LoggerUtils
			.getLogger(OrderService.class);

	@Autowired
	private TOrderDAOImpl orderDAO;

	@Autowired
	private TGoodsDAOImpl goodsDAO;

	@Autowired
	private TOrderHistDAOImpl tOrderHistDAO;

	@Autowired
	private TUsrDAOImpl tUsrDAO;

	@Autowired
	private TCancelOrderDAOImpl tCancelOrderDAO;

	@Transactional
	public List<Order> getHisOrder(String userid, int start, int end,
			String status) {

		GetHistOrdersResp getHistOrdersResp = new GetHistOrdersResp();

		// 查询条件
		List<TOrder> tOrders = orderDAO.findMyorderBySQL(start, end, userid,
				status);
		List<Order> orderList = new ArrayList<Order>();

		if (tOrders != null) {
			for (TOrder tOrder : tOrders) {
				Order order = new Order();
				order.setOrderid(tOrder.getId() + "");
				order.setStatus(tOrder.getStatus());
				order.setNextstatus(CommonDefs.ORDER_NEXT_STATUS_CN_MAP
						.get(tOrder.getStatus()));
				List<OrderGoods> orderGoods = getGoodsList(tOrder);
				int total = 0;
				int totalPrice = 0;
				for (OrderGoods goods : orderGoods) {
					total = total + goods.getNum();
					totalPrice += goods.getActualprice();
				}
				order.setGoodsnum(total);
				order.setTotalprice(totalPrice);
				order.setList(orderGoods);

				/*
				 * myOrder.setStatus(CommonDefs.ORDER_STATUS_CN_MAP.get(tOrder.
				 * getStatus()));
				 * myOrder.setNextStatus(CommonDefs.ORDER_NEXT_STATUS_CN_MAP
				 * .get(tOrder.getStatus()));
				 * myOrder.setOrderid(tOrder.getId()+""); List<OrderGoods>
				 * orderGoods = getGoodsList(tOrder); int total = 0; int
				 * totalPrice = 0; for (OrderGoods goods : orderGoods) { total =
				 * total+goods.getNum(); totalPrice += goods.getActualprice(); }
				 * myOrder.setTotalGood(total+"");
				 * myOrder.setTotalMoney(totalPrice+"");
				 * myOrder.setOrderGoods(orderGoods); myOrders.add(myOrder);
				 */
				orderList.add(order);
			}
		}

		return orderList;

	}

	/**
	 * 根据订单ID查询订单
	 */
	@Transactional
	public TOrder getOrder(String orderid) {
		TOrder tOrder = orderDAO.findById(Integer.parseInt(orderid));
		return tOrder;
	}

	/**
	 * 根据用户ID和
	 */
	public TOrder getOrder(int userid, String status) {
		TOrder orderExample = new TOrder();
		orderExample.setUserid(userid);
		orderExample.setStatus(status);
		List<TOrder> tOrders = orderDAO.findByExample(orderExample);
		if (tOrders != null && tOrders.size() == 0) {
			return null;
		}
		return tOrders.get(0);
	}

	/**
	 * 合并订单列表
	 * */
	private List<OrderGoods> mergeGoodsList(ModifyOrderReq modifyOrderReq,
			TOrder tOrder) {
		String orderStr = tOrder.getGoodslist();
		if (orderStr == null) {
			return modifyOrderReq.getGoodlist();
		}
		List<OrderGoods> orderGoods = OrderGoods.transferToList(orderStr);
		Map<String, OrderGoods> orderGoodsMap = new HashMap<String, OrderGoods>();
		orderGoodsMap = buildOrderGoodsMap(orderGoodsMap, orderGoods);
		orderGoodsMap = buildOrderGoodsMap(orderGoodsMap,
				modifyOrderReq.getGoodlist());
		return new ArrayList<OrderGoods>(orderGoodsMap.values());
	}

	private Map<String, OrderGoods> buildOrderGoodsMap(
			Map<String, OrderGoods> orderGoodsMap, List<OrderGoods> orderGoods) {
		if (orderGoods == null) {
			return orderGoodsMap;
		}
		for (OrderGoods orderGoods2 : orderGoods) {
			if (orderGoodsMap.containsKey(orderGoods2.getGoodsid())) {
				OrderGoods goods = orderGoodsMap.get(orderGoods2.getGoodsid());
				goods.setNum(goods.getNum() + orderGoods2.getNum());
			} else {
				orderGoodsMap.put(orderGoods2.getGoodsid(), orderGoods2);
			}
		}
		return orderGoodsMap;
	}

	@Transactional
	public String addOrder(ModifyOrderReq modifyOrderReq) {

		// 获得user对象
		TUsr tUsr = new TUsr();
		tUsr.setUserid(Integer.parseInt(modifyOrderReq.getUserid()));

		// 先查询用户是否有临时订单， 如果有
		TOrder order = getOrder(tUsr.getUserid(),
				CommonDefs.ORDER_STATUS_TO_CHARGE);

		// TODO 需要校验订单？

		List<OrderGoods> goodsList = null;
		String action = null;
		if (null != order) {
			// 用户本来存在待支付订单，需要合并订单
			goodsList = mergeGoodsList(modifyOrderReq, order);
			action = CommonDefs.ORDER_ACT_MODIFY;
		} else {
			order = new TOrder();
			goodsList = modifyOrderReq.getGoodlist();
			action = CommonDefs.ORDER_ACT_ADD;
		}

		String goodsListStr = OrderGoods.transferFromList(goodsList);
		order.setGoodslist(goodsListStr);

		// 保存地址信息
		AddressInfo address = modifyOrderReq.getAddressinfo();
		if (address != null) {
			order.setAddress(address.getAddress());
			order.setAddressname(address.getAddressname());
			order.setAddressphone(address.getAddressphone());
		}

		// 设置订单历史记录
		recordHist(order, action, tUsr.getUserid());
		// 保存订单
		orderDAO.save(order);

		return order.getId() + "";
	}

	/**
	 * 修改订单
	 * */
	@Transactional
	public String modifyOrder(ModifyOrderReq modifyOrderReq) {

		// 获得user对象
		TUsr tUsr = new TUsr();
		tUsr.setUserid(Integer.parseInt(modifyOrderReq.getUserid()));

		TOrder order = orderDAO.findById(Integer.parseInt(modifyOrderReq
				.getOrderid()));
		// TODO 需要校验订单？

		// TODO 时间戳应该通过数据库的trigger来更新

		List<OrderGoods> goodsList = modifyOrderReq.getGoodlist();
		String goodsListStr = OrderGoods.transferFromList(goodsList);
		order.setGoodslist(goodsListStr);

		// 保存地址信息
		AddressInfo address = modifyOrderReq.getAddressinfo();
		if (address != null) {
			order.setAddress(address.getAddress());
			order.setAddressname(address.getAddressname());
			order.setAddressphone(address.getAddressphone());
		}

		// 设置订单历史记录
		TOrderHist tOrderHist = recordHist(order, CommonDefs.ORDER_ACT_MODIFY,
				tUsr.getUserid());

		// 更新订单
		orderDAO.attachDirty(order);
		tOrderHistDAO.attachDirty(tOrderHist);

		return String.valueOf(order.getId());
	}

	@Transactional
	public String commitOrder(ModifyOrderReq modifyOrderReq) {
		// 获得user对象
		TUsr tUsr = new TUsr();
		tUsr.setUserid(Integer.parseInt(modifyOrderReq.getUserid()));

		TOrder order = orderDAO.findById(Integer.parseInt(modifyOrderReq
				.getOrderid()));

		order.setStatus(CommonDefs.ORDER_STATUS_TO_COMMIT);

		// 设置订单历史记录
		TOrderHist tOrderHist = recordHist(order, CommonDefs.ORDER_ACT_COMMIT,
				tUsr.getUserid());

		// 更新订单到数据库
		orderDAO.attachDirty(order);
		tOrderHistDAO.save(tOrderHist);

		return String.valueOf(order.getId());
	}

	@Transactional
	public String cancelOrder(ModifyOrderReq modifyOrderReq)
			throws SalesLogicalException {
		// 获得user对象
		TUsr tUsr = new TUsr();
		tUsr.setUserid(Integer.parseInt(modifyOrderReq.getUserid()));

		TOrder order = orderDAO.findById(Integer.parseInt(modifyOrderReq
				.getOrderid()));
		order.setStatus(CommonDefs.ORDER_STATUS_TO_CANCEL);

		OrderCancelReason cancelReason = modifyOrderReq.getOrdercancelreason();
		if (null == cancelReason) {

			TCancelOrder cancelOrder = new TCancelOrder();
			cancelOrder.setOrderid(order.getId());
			cancelOrder.setBriefreason(cancelReason.getBriefreason());
			cancelOrder.setDetailreason(cancelReason.getDetailreason());
			cancelOrder.setImglist(OrderCancelReason
					.convertImglistToString(cancelReason.getImglist()));
			tCancelOrderDAO.save(cancelOrder);

		} else {
			throw new SalesLogicalException(
					ReturnCode.RET_LOGICAL_ORDER_CANELREASONNULL, "orderid "
							+ order.getId() + "can reason is null ");
		}

		// 设置订单历史记录
		TOrderHist tOrderHist = recordHist(order, CommonDefs.ORDER_ACT_COMMIT,
				tUsr.getUserid());

		// 更新订单到数据库
		orderDAO.attachDirty(order);
		tOrderHistDAO.save(tOrderHist);

		return String.valueOf(order.getId());
	}

	@Transactional
	public String receiveOrder(ModifyOrderReq modifyOrderReq) {
		// 获得user对象
		TUsr tUsr = new TUsr();
		tUsr.setUserid(Integer.parseInt(modifyOrderReq.getUserid()));

		TOrder order = orderDAO.findById(Integer.parseInt(modifyOrderReq
				.getOrderid()));
		order.setStatus(CommonDefs.ORDER_STATUS_FINISHED);

		// 设置订单历史记录
		TOrderHist tOrderHist = recordHist(order, CommonDefs.ORDER_ACT_COMMIT,
				tUsr.getUserid());

		// 更新订单到数据库
		orderDAO.attachDirty(order);
		tOrderHistDAO.save(tOrderHist);

		return String.valueOf(order.getId());
	}

	/**
	 * 记录订单历史记录
	 * */
	private TOrderHist recordHist(TOrder order, String action, int userid) {

		TOrderHist tOrderHist = new TOrderHist();
		tOrderHist.setOperation(action);
		tOrderHist.setOperator(userid);
		tOrderHist.setGoodslist(order.getGoodslist());
		tOrderHist.setStatus(order.getStatus());

		// 保存地址信息
		tOrderHist.setAddress(order.getAddress());
		tOrderHist.setAddressname(order.getAddressname());
		tOrderHist.setAddressphone(order.getAddressphone());

		return tOrderHist;

	}

	public List<OrderGoods> getGoodsList(TOrder order) {
		String goodsListStr = order.getGoodslist();
		if (null == goodsListStr) {
			return null;
		}

		List<OrderGoods> list = OrderGoods.transferToList(goodsListStr);
		for (OrderGoods goods : list) {
			// 从数据库将商品信息查询出来， 补全其他字段
			Integer goodsid = Integer.valueOf(goods.getGoodsid());
			TGoods tGoods = goodsDAO.findById(goodsid);
			goods.transferFromTGoods(tGoods);
		}

		return list;
	}

	public TOrderDAOImpl getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(TOrderDAOImpl orderDAO) {
		this.orderDAO = orderDAO;
	}

}
