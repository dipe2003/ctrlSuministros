$(document).ready(function(){
    $("#loading-container").show();
});
$(window).load(function(){
    $("#loading-container").hide();
});
function indicador(data){
    if(data.status==='begin'){
       $("#loading").show();
    }
    if(data.status === 'complete'){
        $("#loading").hide();
    }
};
