/*
 * Custom JS function to load the Facility Widget
 */
function initFrsWidget(userRoleId, token, facilityId, baseServiceUrl){

	var frsJS="/FacilityManagement/FacilityWidget/src/FacilityManagementWidget.js";
	var frsCSS="/FacilityManagement/FacilityWidget/FacilityManagementStyles.css";
	
	if(checkIfScriptExists(baseServiceUrl+frsJS)){
		initializeFacilityWidget(userRoleId, token, facilityId, baseServiceUrl);
	}else{
	    var script = document.createElement("script");
	    document.body.insertBefore(script, this.firstChild);
	    script.onload=function(){
	        var cssLink = document.createElement("link");
	        cssLink.rel = 'stylesheet';  
	        cssLink.type = 'text/css'; 
	        document.head.insertBefore(cssLink, this.firstChild);
	        cssLink.onload=function(){
	        	initializeFacilityWidget(userRoleId, token, facilityId, baseServiceUrl);
	        };
	        cssLink.href=baseServiceUrl+frsCSS;
	    }
	    script.setAttribute('src', baseServiceUrl+frsJS);
	}
}

function initializeFacilityWidget(userRoleId, token, facilityId, baseServiceUrl){
	$frs.initFacilityManagementWidget({
        widgetDisplayType: "Single Facility View Only",
        ImagesFolderPath: baseServiceUrl+"/ContentFramework/FRS%20Widget/images/",
        baseServiceUrl: baseServiceUrl,
        userRoleId: userRoleId,
        filesAlreadyIncluded : true,
        isRegistration: false, //static
        loadFromSession: false, //static
        NASSToken: token,
        NAASip: "127.0.0.1",
        EditingFacilityIDType: 'ProgramSystemId',
        EditingFacilityID: facilityId,
        editFacilityButtonText: "Save",
        onServiceError: function (msg, location) {
           // Do something
        },
        onWidgetDataLoaded: function(){
        	//adjusting the sub-facility exit button
        	var el=document.getElementById("details-sub-facility-exit");
        	el.style.left="25px";
        }
     });
}

function checkIfScriptExists(url){
    var scripts = document.getElementsByTagName('script');
    for (var i = scripts.length; i--;) {
        if (scripts[i].src == url) return true;
    }
    return false;
}

function destroyFrsWidget(){
	location.reload();
}
