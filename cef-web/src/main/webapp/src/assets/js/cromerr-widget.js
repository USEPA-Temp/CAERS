/*
 * Custom JS function to load the Cromerr Widget
 */
function initCromerrWidget(userRoleId, token, baseServiceUrl, emissionsReportId, facilitySiteId, toastr, emitter, userFeedbackEnabled, feedbackSubmitted){

    var blockUIJS = "assets/js/jquery.blockUI.js";
    var cromerrJS="/Content/CromerrWidget/cromerrWidget.webpack.min.js";
    var jqueryJS="assets/js/jquery-3.5.1.min.js";

    if (checkIfScriptExists(baseServiceUrl+cromerrJS)) {
        initializeCromerrWidget(userRoleId, token, baseServiceUrl, emissionsReportId, facilitySiteId, toastr, emitter, userFeedbackEnabled, feedbackSubmitted);
    } else {
        var jqueryScript=loadScript(jqueryJS);
        jqueryScript.onload=function(){
            var blockUIScript=loadScript(blockUIJS);
            blockUIScript.onload=function(){
                var cromerrScript=loadScript(baseServiceUrl+cromerrJS);
                cromerrScript.onload=function(){
                    initializeCromerrWidget(userRoleId, token, baseServiceUrl, emissionsReportId, facilitySiteId, toastr, emitter, userFeedbackEnabled, feedbackSubmitted);
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

function initializeCromerrWidget(userId, userRoleId, baseServiceUrl, emissionsReportId, facilitySiteId, toastr, emitter, feedbackEnabled, feedbackSubmitted){
    $( document ).ready(function() {
        $cromerrWidget.initializeCromerrWidget({
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
                $.blockUI();
                $.ajax({
                      url: "api/emissionsReport/submitToCromerr",
                      type: "get", //send it through get method
                      data: {
                        activityId: event.activityId,
                        reportId: emissionsReportId
                      },
                      success: function(response) {
                        //if feedback is enabled and the user hasn't already submitted feedback for this report go to feedback page after signing
                        if (feedbackEnabled && !feedbackSubmitted) {
                            toastr.success('', "The Emission Report has been successfully electronically signed and submitted to the agency for review.");
                            $.unblockUI();
                            window.location.href="/cef-web/#/facility/"+facilitySiteId+"/report/"+emissionsReportId+"/userfeedback";
                        } else {
                            toastr.success('', "The Emission Report has been successfully electronically signed and submitted to the agency for review.");
                            $.unblockUI();
                            window.location.href="/cef-web/#/facility/"+facilitySiteId+"/report";
                        }
                      },
                      error: function(xhr) {
                          $.unblockUI();
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
