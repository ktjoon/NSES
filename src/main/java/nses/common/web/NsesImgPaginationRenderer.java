package nses.common.web;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;

public class NsesImgPaginationRenderer extends AbstractPaginationRenderer{
	
	public NsesImgPaginationRenderer() {
		firstPageLabel = "";
        previousPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">" +
        						"<span>&laquo;</span></a></li>";
        currentPageLabel = "<li class='active'><span>{0}</span></li>";
        otherPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a></li>";
        nextPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">" +
        					"<span>&raquo;</span></a></li>";
        lastPageLabel = "";
	}
}
