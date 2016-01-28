/**
 * 지도에 라인/원/사각형 표출
 */

MapLine = function() {
	this.firstCoord	= null;
	this.lastCoord	= null;
	this.obj		= null;
	this.path		= null;
};
MapLine.prototype = {
	setFirstCoord : function(coord) {
		this.firstCoord	= coord;
		if (this.path == null) {
			this.path = new olleh.maps.Path([
				this.firstCoord,
				this.firstCoord
			]);
		}
		else {
			this.path.setAt(0, this.firstCoord);
		}
	},
	setLastCoord : function(coord) {
		this.lastCoord	= coord;
		this.path.setAt(1, this.lastCoord);
	},
	getFirstCoord : function() {
		return this.firstCoord;
	},
	getLastCoord : function() {
		return this.lastCoord;
	},
	makeLine : function() {
		if (this.obj == null) {
			this.obj = new olleh.maps.vector.Polyline({
				map: smap.getMap(),
				path: this.path,
				strokeColor: 'blue',
				strokeOpacity: .5,
				strokeWeight: 5
			});
		}
		else {
			this.obj.setPath(this.path);
			this.obj.setVisible(true);
		}
	}, 
	clear : function() {
		if (this.obj != null) {
			this.obj.setVisible(false);
		}
	}
};


//
MapCircle = function() {
	this.firstCoord	= null;
	this.lastCoord	= null;
	this.radius		= 0;
	this.obj		= null;
	this.tooltip	= null;
};
MapCircle.prototype = {
	setFirstCoord : function(coord) {
		this.firstCoord = coord;
	},
	setLastCoord : function(coord) {
		this.lastCoord 	= coord;
	},
	showTooltip : function(stext, coord) {
		if (this.tooltip == null) {
			this.tooltip = new olleh.maps.overlay.Tooltip({
				pixelOffset: new olleh.maps.Point(5, 5),
				position: coord,
				content: stext
			});
			this.tooltip.open(smap.getMap(), coord);
		}
		else {
			this.tooltip.setContent(stext);
			this.tooltip.setPosition(coord);
			this.tooltip.setVisible(true);
		}
	},
	hideTooltip : function() {
		if (this.tooltip != null) {
			this.tooltip.setVisible(false);
		}
	},
	calcRadius : function() {
		if (this.firstCoord == null) {
			return;
		}
		if (this.lastCoord == null) {
			return;
		}
		
		var fc	= this.firstCoord;
		var lc	= this.lastCoord;
		this.radius	= fc.distanceTo(lc);
	},
	makeCircle : function() {
		this.calcRadius();
		this.obj = new olleh.maps.vector.Circle({
			map: smap.getMap(),
			center: this.firstCoord,
			radius: this.radius,
			strokeColor: 'red',
			fillColor: 'red',
			fillOpacity: 0.3
		});
		var	sText = '<div style="width: 100px;">반경: ' + this.radius.toFixed(1) + 'm</div>';
		cmLog(sText);
		this.showTooltip(sText, this.lastCoord);
	}, 
	clear : function() {
		if (this.obj != null)
			this.obj.setVisible(false);
		this.hideTooltip();
	}
};

MapRect = function() {
	this.firstCoord	= null;
	this.lastCoord	= null;
	this.obj		= null;
	this.tooltip	= null;
};
MapRect.prototype = {
	setFirstCoord : function(coord) {
		this.firstCoord = coord;
	},
	setLastCoord : function(coord) {
		this.lastCoord 	= coord;
	},
	getBounds : function() {
		return this.obj.getBounds();
	},
	showTooltip : function(stext, coord) {
		if (this.tooltip == null) {
			this.tooltip = new olleh.maps.overlay.Tooltip({
				pixelOffset: new olleh.maps.Point(5, 5),
				position: coord,
				content: stext
			});
			this.tooltip.open(smap.getMap(), coord);
		}
		else {
			this.tooltip.setContent(stext);
			this.tooltip.setPosition(coord);
			this.tooltip.setVisible(true);
		}
	},
	hideTooltip : function() {
		if (this.tooltip != null) {
			this.tooltip.setVisible(false);
		}
	},
	getDistanceString : function() {
		if (this.firstCoord == null) {
			return '';
		}
		if (this.lastCoord == null) {
			return '';
		}
		
		var fc	= this.firstCoord;
		var lc	= this.lastCoord;
		var cx	= new olleh.maps.UTMK(lc.x, fc.y);
		var cy	= new olleh.maps.UTMK(fc.x, lc.y);
		var	distX = fc.distanceTo(cx);
		var	distY = fc.distanceTo(cy);
		var	sText = distX.toFixed(0) + ' x ' + distY.toFixed(0) + ' m';
		
		return sText;
	},
	makeRect : function() {
		var oBounds	= recalcDrawRect(this.firstCoord, this.lastCoord);

		if (this.obj == null) {
			this.obj = new olleh.maps.vector.Rectangle({
				bounds : oBounds,
				map : smap.getMap(),
				strokeColor: 'red',
				fillColor: 'red',
				fillOpacity: 0.3
			});
		}
		
		this.obj.setBounds(oBounds);
		this.obj.setVisible(true);
		
		var	sText = this.getDistanceString();
		this.showTooltip(sText, this.lastCoord);
	}, 
	clear : function() {
		if (this.obj != null) {
			this.obj.setVisible(false);
		}
		this.hideTooltip();
	}
};

function recalcDrawRect(lbCoord, rtCoord) {
	var bounds			= null;
	var boundCoordx		= 0;
	var boundCoordy		= 0;
	
	bounds	= new olleh.maps.Bounds(lbCoord, rtCoord);
	
	if (bounds.getWidth() <= 0 && bounds.getHeight() <= 0) {
		var fx	= rtCoord.x;
		var fy	= rtCoord.y;
		var lx	= lbCoord.x;
		var ly	= lbCoord.y;
		
		boundCoordx	= new olleh.maps.UTMK(fx, fy);
		boundCoordy	= new olleh.maps.UTMK(lx, ly);
		
		bounds	= new olleh.maps.Bounds(boundCoordx, boundCoordy);
		
	} else if (bounds.getWidth() <= 0) {
		var fx	= rtCoord.x;
		var fy	= lbCoord.y;
		var lx	= lbCoord.x;
		var ly	= rtCoord.y;
		
		boundCoordx	= new olleh.maps.UTMK(fx, fy);
		boundCoordy	= new olleh.maps.UTMK(lx, ly);
		
		bounds	= new olleh.maps.Bounds(boundCoordx, boundCoordy);
		
	} else if (bounds.getHeight() <= 0){
		var fx	= lbCoord.x;
		var fy	= rtCoord.y;
		var lx	= rtCoord.x;
		var ly	= lbCoord.y;
		
		boundCoordx	= new olleh.maps.UTMK(fx, fy);
		boundCoordy	= new olleh.maps.UTMK(lx, ly);
		
		bounds	= new olleh.maps.Bounds(boundCoordx, boundCoordy);
	}
	
	return bounds;
}
