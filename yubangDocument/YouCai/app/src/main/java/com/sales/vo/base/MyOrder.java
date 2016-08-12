package com.sales.vo.base;

import java.util.List;

public class MyOrder {

	//订单号
		private String orderid;
		
		//订单状态
		private String status;
		
		//订单商品
		private List<OrderGoods> orderGoods;
		
		//订单金额
		private String totalMoney;
		
		//商品数量
		private String totalGood;
		
		//下一个状态
		private String nextStatus;

		public String getOrderid() {
			return orderid;
		}

		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public List<OrderGoods> getOrderGoods() {
			return orderGoods;
		}

		public void setOrderGoods(List<OrderGoods> orderGoods) {
			this.orderGoods = orderGoods;
		}

		public String getTotalMoney() {
			return totalMoney;
		}

		public void setTotalMoney(String totalMoney) {
			this.totalMoney = totalMoney;
		}

		public String getTotalGood() {
			return totalGood;
		}

		public void setTotalGood(String totalGood) {
			this.totalGood = totalGood;
		}

		public String getNextStatus() {
			return nextStatus;
		}

		public void setNextStatus(String nextStatus) {
			this.nextStatus = nextStatus;
		}
}
