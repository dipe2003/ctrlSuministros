$(document).ready(function(){
    $("#frmRegIngresoSuministro\\:inputCantidadIngresoSuministro").val("");
    $("#frmRegIngresoSuministro\\:inputNumeroLoteSuministro").focusout(function(){
        $("#frmRegIngresoSuministro\\:btnNumLote").click();
    });
});