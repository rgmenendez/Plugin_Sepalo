# cordova-plugin-sepalo

Plugin personal para utilizar lector de tarjetas y librería de Sepalo en OBTABLET. Se basa en plugin-template y tiene método "echo(..)" para probar que devuelve el mensaje pasado.



## Getting started
Para incluir plugin en proyecto OBTABLET, descargar este proyecto en la carpeta que contiene OBTABLET (c:\Proyectos\workspace_OBT_2022\) y ejecutar:

cordova plugin add c:\Proyectos\workspace_OBT_2022\cordova-plugin-sepalo
cordova plugin rm cordova-plugin-sepalo

Once you're familiar with that process, you may install this plugin with the [Cordova CLI](https://cordova.apache.org/docs/en/4.0.0/guide_cli_index.md.html):

```
Basado en el siguiente plugin:
https://github.com/hirtenfelder/cordova-plugin-template.git

donde se ha cambiado toda referencia template por sepalo.
```

## Usage

If the plugin was added to your Cordova (or Ionic) project, you may use it like that:

```
navigator.sepalo.echo({string} echoValue, {function} successCallback);
```