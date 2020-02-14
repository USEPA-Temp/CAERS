/*
 * Custom JS function to load the Cromerr Widget
 */
function initCromerrWidget(userRoleId, token, baseServiceUrl, emissionsReportId, facilitySiteId, toastr, emitter){

    var fancyboxJS="/ContentFramework/v3/js/third-party/fancybox-v2/jquery.fancybox.js";
    var fancyboxCSS="/ContentFramework/v3/js/third-party/fancybox-v2/jquery.fancybox.css";
    var jqueryValidate="/ContentFramework/Cromerr/js/jquery.validate.min.js";
    var cromerrJS="/ContentFramework/Cromerr/js/serverWidget.js";
    var jqueryJS="assets/js/jquery-1.6.4.min.js";

    if(checkIfScriptExists(baseServiceUrl+cromerrJS)){
        initializeCromerrWidget(userRoleId, token, baseServiceUrl, emissionsReportId, facilitySiteId, toastr, emitter);
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
                var cromerrScript=loadScript(baseServiceUrl+cromerrJS);
                cromerrScript.onload=function(){
                    initializeCromerrWidget(userRoleId, token, baseServiceUrl, emissionsReportId, facilitySiteId, toastr, emitter);
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

function initializeCromerrWidget(userId, userRoleId, baseServiceUrl, emissionsReportId, facilitySiteId, toastr, emitter){
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
                          toastr.success('', "The Emission Report has been successfully electronically signed and submitted to the agency for review.");
                          event.unblockUI();
                          window.location.href="/cef-web/#/facility/"+facilitySiteId+"/report";
                      },
                      error: function(xhr) {
                          event.unblockUI();
                          toastr.error('', "There was an error electronically signing your emission report. Please try again.");
                      }
                    });
            },
            error : function(error) {
                toastr.error('', "There was an error electronically signing your emission report. Please try again.");
                console.log(error);
            },
            cancel: function() {
            }
        });
        emitter.next(true);
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
