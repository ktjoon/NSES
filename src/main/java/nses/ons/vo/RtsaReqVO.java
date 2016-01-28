/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nses.ons.vo;

import java.util.List;


/**
 * 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.12.23              최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2014.12.23
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSES All right reserved.
 */
public class RtsaReqVO {
	
	private	String				car_no = null;
	private	List<RouteInfo>		routes = null;
	
	
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public List<RouteInfo> getRoutes() {
		return routes;
	}
	public void setRoutes(List<RouteInfo> routes) {
		this.routes = routes;
	}
	
	//
	public void dump() {
		System.out.println("car_no: " + car_no);
		
		if (routes != null) {
			for (int i = 0; i < routes.size(); i ++) {
				RouteInfo	info = routes.get(i);
				System.out.println("link_idx: " + info.link_idx + ", point: [" + info.point.x + ", " + info.point.y + "]");
			}
		}
	}
	
	//
	public static class RouteInfo {
		public	int			link_idx;
		public	String		type;
		public	Coord		point;
		public	int			tdp_type = 5;
	}
	public static class Coord {
		public	double		x;
		public	double		y;
	}
}
