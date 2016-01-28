<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
﻿<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=no; target-densitydpi=medium-dpi" />

<script src="../js/libs/jquery.1.10.js"></script>
<script src="../js/libs/PluginDetect_VLC.js"></script>

<script type="text/javascript">
//
var	g_version = null;

//
function getVersion() {
	return g_version;
}

$(document).ready(function(){
	g_version = PluginDetect.getVersion('vlc');
});

</script>
</head>

<body>
</body>
</html>
