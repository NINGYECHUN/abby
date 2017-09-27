package com.esm.util;

import java.util.List;

public class ListView<T> {

		/**
		 * 行数据.
		 */
		private List<T> rows;
		
		/**
		 * 有多少行.
		 */
		private Integer totalRows;

		public List<T> getRows() {
			return rows;
		}

		public void setRows(List<T> rows) {
			this.rows = rows;
		}

		public Integer getTotalRows() {
			return totalRows;
		}

		public void setTotalRows(Integer totalRows) {
			this.totalRows = totalRows;
		}
}
