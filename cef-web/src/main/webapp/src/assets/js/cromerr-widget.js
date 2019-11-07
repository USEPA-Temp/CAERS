/*
 * Custom JS function to load the Cromerr Widget
 */
function initCromerrWidget(userRoleId, token, baseServiceUrl, emissionsReportId, facilitySiteId){

	var fancyboxJS="/ContentFramework/Cromerr/js/fancybox/jquery.fancybox-1.3.4.js";
	var fancyboxCSS="/ContentFramework/Cromerr/js/fancybox/jquery.fancybox-1.3.4.css";
	var jqueryValidate="/ContentFramework/Cromerr/js/jquery.validate.min.js";
	var jqueryBlockUI="/ContentFramework/Cromerr/js/jquery.blockUI.js";
	var cromerrJS="/ContentFramework/Cromerr/js/serverWidget.js";
	var jqueryJS="assets/js/jquery-1.6.4.min.js";

	if(checkIfScriptExists(baseServiceUrl+cromerrJS)){
		initializeCromerrWidget(userRoleId, token, baseServiceUrl, emissionsReportId, facilitySiteId);
	}else{
		var jqueryScript=loadScript(jqueryJS);
		jqueryScript.onload=function(){
			var fancyboxScript=loadScript(baseServiceUrl+fancyboxJS);
			fancyboxScript.onload=function(){
		        var cssLink = document.createElement("link");
		        cssLink.rel = 'stylesheet';
		        cssLink.type = 'text/css';
		        document.head.insertBefore(cssLink, this.firstChild);
		        cssLink.href=baseServiceUrl+fancyboxCSS;
				loadScript(baseServiceUrl+jqueryValidate);
				loadScript(baseServiceUrl+jqueryBlockUI);
				var cromerrScript=loadScript(baseServiceUrl+cromerrJS);
				cromerrScript.onload=function(){
			    	initializeCromerrWidget(userRoleId, token, baseServiceUrl, emissionsReportId, facilitySiteId);
			    }
				cromerrScript.setAttribute('id', "cromerrServerSign");
		    }
		}
	}
}


function loadScript(srciptUrl){
    var script = document.createElement("script");
    document.body.insertBefore(script, this.firstChild);
    script.setAttribute('src', srciptUrl);
    return script;
}

function initializeCromerrWidget(userId, userRoleId, baseServiceUrl, emissionsReportId, facilitySiteId){
	$( document ).ready(function() {
	    $.cromerrWidget({
	        esignButtonId : "certifyAndSubmit",
	        widgetParams : {
	            dataflow : "CAER",
	            userId : userId,
	            userRoleId : userRoleId,
	            props : {
	                activityDescription : 'Combined Air Emissions Reporting Submission'
	            }
	        },
	        success : function(event) {
	        	event.blockUI();
	        	$.ajax({
	        		  url: "api/emissionsReport/submitToCromerr",
	        		  type: "get", //send it through get method
	        		  data: {
	        			activityId: event.activityId,
	        		    reportId: emissionsReportId
	        		  },
	        		  success: function(response) {
	        			  alert("Submission successful.");
	        			  event.unblockUI();
	        			  window.location.href="/cef-web/#/facility/"+facilitySiteId+"/report";
	        		  },
	        		  error: function(xhr) {
	        			  event.unblockUI();
	        			  alert("Submission error.");
	        		  }
	        		});
	        },
	        error : function(error) {
	        	alert("Submission error.");
	        	console.log(error);
	        },
	        cancel : function() {
	        	alert("Submission cancelled.");
	        }
	    });
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
