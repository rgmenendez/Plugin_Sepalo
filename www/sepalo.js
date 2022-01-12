/**
 * Cordova Sepalo Plugin
 */

var Sepalo = (function () { 
    
    function Sepalo() {
    }
    
    Sepalo.prototype.echo = function (echoValue, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'echo', [echoValue]);
    };

    Sepalo.prototype.startOperationJA = function(importe, importe_dto,successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'startOperationJA', [importe, importe_dto]);
    };
    
    Sepalo.prototype.startOperationKeyJA = function(successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'startOperationKeyJA', []);
    };
    
    Sepalo.prototype.startOperationJE = function(successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'startOperationJE', []);
    }; 
    
    Sepalo.prototype.startOperationConsultaMon = function(tipoTarjeta,successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'startOperationConsultaMon', [tipoTarjeta]);
    };
    
    Sepalo.prototype.startOperationConsultaAb = function(tipoTarjeta,successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'startOperationConsultaAb', [tipoTarjeta]);
    };  
    
    Sepalo.prototype.startPagoMonedero = function(tipoTarjeta,importe, successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'startPagoMonedero', [tipoTarjeta,importe]);
    };
    
    Sepalo.prototype.startPagoAbono = function(tipoTarjeta,datosSepalo, successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'startPagoAbono', [tipoTarjeta,datosSepalo]);
    };
    
    Sepalo.prototype.startCargaMonedero = function(tipoTarjeta,datosSepalo, successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'startCargaMonedero', [tipoTarjeta,datosSepalo]);
    };
    
    Sepalo.prototype.startCargaAbono = function(tipoTarjeta,datosSepalo, successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'Sepalo', 'startCargaAbono', [tipoTarjeta,datosSepalo]);
    };

    return Sepalo;

})();

var sepalo = new Sepalo();
module.exports = sepalo;