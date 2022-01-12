package cordova.plugin;

//import org.apache.cordova.*;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
//import org.apache.cordova.PluginResult;
//import org.apache.cordova.PluginResult.Status;

import android.content.Intent;
//import org.apache.cordova.api.CallbackContext;
//import org.apache.cordova.api.CordovaPlugin;
//import org.apache.cordova.api.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
//import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;

//import com.alsa.alsaconnect.R;
import com.alsa.alsaopprivlib.BluetoothReader;
import com.alsa.alsaopprivlib.NfcReader;
import com.alsa.alsaopprivlib.reader.BluetoothReaderImpl;
import com.alsa.alsaopprivlib.reader.NfcReaderImpl;



import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Set;
import java.util.Date;

public class Sepalo extends CordovaPlugin {
    
    public static String LOGCATEGORY = "Sepalo";

    protected CallbackContext callbackContextConnect;    

    protected BluetoothReaderImpl reader;
	protected NfcReaderImpl readerNfc;
	
	private String datos="";
    private String tipoTarjeta="";
    protected boolean status=true;
	
	/** buttons */
	protected Button andaluciaButton;	
	protected Button extremaduraButton;
	
	/**
	 * Disabled buttons.
	 */
	public void disabledPaymentScreen() {
		//andaluciaButton.setBackgroundResource(R.color.gray);
		andaluciaButton.setEnabled(false);
		//extremaduraButton.setBackgroundResource(R.color.gray);
		extremaduraButton.setEnabled(false);		
    }
    
    /*Constructor */
    public Sepalo() {
    	
    }
    	
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(final String action,final JSONArray args,final CallbackContext callbackContextConnect) 
        throws JSONException 
    {
        this.callbackContextConnect = callbackContextConnect;
        
        status = true;
        String result = "";
        Log.d(LOGCATEGORY, "Sepalo action " + action);
        
        String importe="";
        String importe_dto="";        
        
        Calendar hoy = new GregorianCalendar();
                       
        String fecha= new SimpleDateFormat("ddMMyy").format(hoy.getTime());
        String hora = new SimpleDateFormat("HHmm").format(hoy.getTime());
       
        
        if (action.equals("echo")) {
            callbackContextConnect.success("Echo echo Sepalo -> " + args.getString(0));
            return true;
        } else if (action.equals("startOperationJA")) 
        {
            try
            {   
                // Tarjeta Junta de Andalucia
                Log.d(LOGCATEGORY, "Sepalo startOperationJA");
                this.callbackContextConnect = callbackContextConnect;
                
                if (args != null && args.length()== 2 ) {                    
                    //numTarjeta = args.getString(0);
                    importe=args.getString(0); // Debe tener 6 digitos
                    importe_dto= args.getString(1);  // Debe tener 6 digitos 
                    this.datos="43" + importe + importe_dto + fecha + hora;
                } else
                { // Falta argumentos para Junta de Andalucía.
                    // callbackContextError -> enviar mensaje de Error
                    Sepalo.this.callbackContextConnect.error("Es necesario indicar importe e importe descuento");	
                } 
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {
                            //Looper.prepare();
                            /*bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                            if (bluetoothAdapter == null) {
                                // Bluetooth is not supported, display notification and finish this activity
                                Toast.makeText(cordova.getActivity().getApplicationContext(), "Bluetooth no soportado", Toast.LENGTH_LONG).show();

                            }

                            //Check whether Bluetooth is turned on
                            boolean btStackAtStartup = bluetoothAdapter.isEnabled();
                            if (btStackAtStartup == false) {
                                cordova.startActivityForResult((CordovaPlugin) Sepalo.this, new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), ACTION_REQUEST_ENABLE_BT_RESULT);

                            }*/
                            if (Sepalo.this.reader == null) {
                                Log.d(LOGCATEGORY, "Sepalo iniciando conexión");

                                Intent SepaloIntent = null;
                                SepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), BluetoothReader.class);
                                
                                
                                SepaloIntent.setPackage("com.alsa.alsaopprivlib");
                                //SepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, "430010000000001203011100");
                                SepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this, SepaloIntent,1);
                                
                                
                                /*Intent intent = null;
                                intent = new Intent(cordova.getActivity().getApplicationContext(), BluetoothReader.class);
                                intent.putExtra(BluetoothReader.DATA_TO_SEND, "430010000000001203011100");
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this,intent, 1);*/
                        
                            } else {
                                Log.d(LOGCATEGORY, "servicio ya iniciado");                                                    
                                Sepalo.this.callbackContextConnect.success("\"\"");
                            }
                        } catch (Exception eA) {
                            // TODO Auto-generated catch block
                            eA.printStackTrace();
                            status=false;
                            Sepalo.this.callbackContextConnect.error("[startOperationJA] Error: " + eA.getMessage());
                        }
                    }
                });
                return status;
            } catch (Exception err)
            {
                err.printStackTrace();
                callbackContextConnect.error("[execute] Error Sepalo: " + err.getMessage());
                return false;
            }
        } else if (action.equals("startOperationKeyJA")) 
        {
            try
            {
                // Tarjeta Junta de Andalucia
                Log.d(LOGCATEGORY, "Sepalo startOperationKeyJA");
                this.callbackContextConnect = callbackContextConnect;
                this.datos="30";
                
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {
                            //Looper.prepare();                            
                            if (Sepalo.this.reader == null) {
                                Log.d(LOGCATEGORY, "Sepalo iniciando conexión");

                                Intent sepaloIntent = null; 
                                sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), BluetoothReader.class);
                                
                                //sepaloIntent.setPackage("com.alsa.alsaopprivlib");
                                //sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, "30");
                                sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this, sepaloIntent,1);                                
                                //cordova.getActivity().startActivityForResult( sepaloIntent,1);
                                                                    
                            } else {
                                Log.d(LOGCATEGORY, "servicio ya iniciado");                                                                
                                Sepalo.this.callbackContextConnect.success("\"\"");
                            }
                        } catch (Exception eA) {
                            // TODO Auto-generated catch block
                            eA.printStackTrace();
                            status=false;
                            Sepalo.this.callbackContextConnect.error("[startOperationKeyJA] Error: " + eA.getMessage());
                        }
                    }
                });
                return status;  
            } catch (Exception err)
            {
                err.printStackTrace();
                callbackContextConnect.error("[execute] Error startOperationKeyJA: " + err.getMessage());
                return false;
            }          
        } else if (action.equals("startOperationJE")) 
        {
            try
            {
                // Tarjeta Junta de Extremadura
                Log.d(LOGCATEGORY, "Sepalo startOperationJE");
                this.callbackContextConnect = callbackContextConnect;
                this.datos="41";
                
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {                                                        
                            if (Sepalo.this.reader == null) {
                                Log.d(LOGCATEGORY, "Sepalo iniciando conexión");

                                Intent sepaloIntent = null; 
                                sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), BluetoothReader.class);
                                                                
                                //sepaloIntent.setPackage("com.alsa.alsaopprivlib");
                                //sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, "430010000000001203011100");
                                sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this, sepaloIntent,1);                                                                    

                            } else {
                                Log.d(LOGCATEGORY, "servicio ya iniciado");                                                                
                                Sepalo.this.callbackContextConnect.success("\"\"");
                            }
                        } catch (Exception eEx) {
                            // TODO Auto-generated catch block
                            eEx.printStackTrace();    
                            status=false;            			
                            Sepalo.this.callbackContextConnect.error("[startOperationJE] Error: " + eEx.getMessage());
                        }
                    }
                });   
            } catch (Exception err)
            {
                err.printStackTrace();
                callbackContextConnect.error("[execute] Error startOperationJE: " + err.getMessage());
                return false;
            }    
            return status;
        } else if (action.equals("startOperationConsultaMon")) 
        {
            try
            {
                // Consulta tarjeta Monedero
                Log.d(LOGCATEGORY, "Sepalo startOperationConsultaMon");
                this.callbackContextConnect = callbackContextConnect;
                this.datos="51"; // Consulta Monedero BusPlus                
                if (args != null) 
                {                    
                    this.tipoTarjeta = args.getString(0);
                    if (this.tipoTarjeta.equals("3"))
                    {
                        this.datos="20"; // Consulta monedero Cantabria
                    }
                }                 
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {
                            if (Sepalo.this.reader == null) {
                                Log.d(LOGCATEGORY, "Sepalo iniciando conexión");

                                Intent sepaloIntent = null; 
                                
                                if (tipoTarjeta.equals("3"))
                                {
                                    // Consulta monedero Cantabria
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), NfcReader.class);
                                    sepaloIntent.putExtra(NfcReader.DATA_TO_SEND, datos);
                                } else
                                {
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), BluetoothReader.class);
                                    sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);                                	
                                }
                                
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this, sepaloIntent,1);

                            } else {
                                Log.d(LOGCATEGORY, "servicio ya iniciado");                                                                
                                Sepalo.this.callbackContextConnect.success("\"\"");
                            }
                        } catch (Exception eEx) {
                            // TODO Auto-generated catch block
                            eEx.printStackTrace();   
                            status=false;             			
                            Sepalo.this.callbackContextConnect.error("[startOperationConsultaMon] Error: " + eEx.getMessage());
                        }
                    }
                });   
                return status;
            } catch (Exception err)
            {
                err.printStackTrace();
                callbackContextConnect.error("[execute] Error startOperationConsultaMon: " + err.getMessage());
                return false;
            }   
        } if (action.equals("startPagoMonedero")) 
        {
            try
            {
                // Pago Monedero
                Log.d(LOGCATEGORY, "Sepalo startPagoMonedero");
                this.callbackContextConnect = callbackContextConnect;
                
                this.datos="33"; // Consulta Monedero BusPlus                
                if (args != null) 
                {                    
                    tipoTarjeta = args.getString(0);
                    if (tipoTarjeta.equals("3"))
                    {
                        this.datos="30"; // Pago monedero Cantabria (en especificaciones pone 32)                    	
                        this.datos += args.getString(1); // importe + linea + estación + equipo + trayecto + bus
                    } else
                    {
                        importe=args.getString(1); // Debe tener 6 digitos
                        this.datos += importe;
                    }
                    
                }  else
                { // Faltan argumentos pago monedero
                // callbackContextError -> enviar mensaje de Error
                    Sepalo.this.callbackContextConnect.error("Es necesario indicar importe");	
                }                	

                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {                           
                            if (Sepalo.this.reader == null) {
                                Log.d(LOGCATEGORY, "Sepalo iniciando conexión");

                                Intent sepaloIntent = null; 
                                
                                if (tipoTarjeta.equals("3"))
                                {
                                    // Monedero Cantabria
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), NfcReader.class);
                                    sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                } else
                                {                                
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), BluetoothReader.class);
                                    //sepaloIntent.setPackage("com.alsa.alsaopprivlib");                                
                                    sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                }
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this, sepaloIntent,1);                                
                                
                            } else {
                                Log.d(LOGCATEGORY, "servicio ya iniciado");                                                                
                                Sepalo.this.callbackContextConnect.success("\"\"");
                            }
                        } catch (Exception ePMon) {
                            // TODO Auto-generated catch block
                            ePMon.printStackTrace();
                            status=false;
                            Sepalo.this.callbackContextConnect.error("[startPagoMonedero] Error: " + ePMon.getMessage());
                        }
                    }
                });
                return status;
            } catch (Exception err)
            {
                err.printStackTrace();
                callbackContextConnect.error("[execute] Error startPagoMonedero: " + err.getMessage());
                return false;
            }   
        } else if (action.equals("startCargaMonedero")) 
        {
            try
            {
                // Recarga Monedero
                Log.d(LOGCATEGORY, "Sepalo startCargaMonedero");
                this.callbackContextConnect = callbackContextConnect;
                
                this.datos="73"; // Consulta Monedero BusPlus                
                if (args != null) 
                {                    
                    tipoTarjeta = args.getString(0);
                    if (tipoTarjeta.equals("3"))
                    {
                        //this.datos="22"; // Carga monedero Cantabria
                    }                    
                    this.datos += args.getString(1); // Ya se pasan todos los datos de la tarjeta en formato SEPALO
                }  else
                { // Faltan argumentos pago monedero
                // callbackContextError -> enviar mensaje de Error
                    Sepalo.this.callbackContextConnect.error("Es necesario información de la tarjeta");	
                }  
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {                           
                            if (Sepalo.this.reader == null) {
                                Log.d(LOGCATEGORY, "Sepalo iniciando conexión");

                                Intent sepaloIntent = null; 
                                
                                if (tipoTarjeta.equals("3"))
                                {
                                    // Monedero Cantabria
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), NfcReader.class);
                                    sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                } else
                                {
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), BluetoothReader.class);
                                    //sepaloIntent.setPackage("com.alsa.alsaopprivlib");                                
                                    sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                }
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this, sepaloIntent,1);                                
                                
                            } else {
                                Log.d(LOGCATEGORY, "servicio ya iniciado");                                                                
                                Sepalo.this.callbackContextConnect.success("\"\"");
                            }
                        } catch (Exception ePMon) {
                            // TODO Auto-generated catch block
                            ePMon.printStackTrace();
                            status=false;
                            Sepalo.this.callbackContextConnect.error("[startPagoMonedero] Error: " + ePMon.getMessage());
                        }
                    }
                });
                return status;
            } catch (Exception err)
            {
                err.printStackTrace();
                callbackContextConnect.error("[execute] Error startCargaMonedero: " + err.getMessage());
                return false;
            }   
        }  else if (action.equals("startCargaAbono")) 
        {
            try
            {
                // Recarga Abono
                Log.d(LOGCATEGORY, "Sepalo startCargaAbono BONOS");
                this.callbackContextConnect = callbackContextConnect;
                
                this.datos="83"; // Carga Abono BusPlus                
                if (args != null) 
                {                    
                    tipoTarjeta = args.getString(0);
                    if (tipoTarjeta.equals("5"))
                    {
                        this.datos="26"; // Carga abono Cantabria
                    }                    
                    this.datos += args.getString(1); // Ya se pasan todos los datos de la tarjeta en formato SEPALO
                }  else
                { // Faltan argumentos pago monedero
                // callbackContextError -> enviar mensaje de Error
                    Sepalo.this.callbackContextConnect.error("Es necesario información de la tarjeta");	
                }  
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {                           
                            if (Sepalo.this.reader == null) {
                                Log.d(LOGCATEGORY, "Sepalo BONOS iniciando conexión");

                                Intent sepaloIntent = null; 
                                if (tipoTarjeta.equals("5"))
                                {
                                    // Monedero Cantabria
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), NfcReader.class);
                                    sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                } else
                                {
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), BluetoothReader.class);
                                    //sepaloIntent.setPackage("com.alsa.alsaopprivlib");                                
                                    sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                }
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this, sepaloIntent,1);                                
                                
                            } else {
                                Log.d(LOGCATEGORY, "servicio ya iniciado");                                                                
                                Sepalo.this.callbackContextConnect.success("\"\"");
                            }
                        } catch (Exception eCAb) {
                            // TODO Auto-generated catch block
                            eCAb.printStackTrace();
                            status=false;
                            Sepalo.this.callbackContextConnect.error("[startCargaAbono] Error: " + eCAb.getMessage());
                        }
                    }
                });
                return status;
            } catch (Exception err)
            {
                err.printStackTrace();
                callbackContextConnect.error("[execute] Error startCargaAbono: " + err.getMessage());
                return false;
            }   
        } else if (action.equals("startOperationConsultaAb")) 
        {
            try
            {
                // Consulta tarjeta Abono
                Log.d(LOGCATEGORY, "Sepalo BONOS startOperationConsultaAb");
                this.callbackContextConnect = callbackContextConnect;
                this.datos="61"; // Consulta Monedero BusPlus                
                if (args != null) {                    
                    tipoTarjeta = args.getString(0);
                    if (tipoTarjeta.equals("5"))
                    {
                        this.datos="22"; // Consulta Abono Cantabria
                    }
                }                 
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {
                            if (Sepalo.this.reader == null) {
                                Log.d(LOGCATEGORY, "Sepalo BONOS iniciando conexión");

                                Intent sepaloIntent = null;                                
                                
                                if (tipoTarjeta.equals("5"))
                                {
                                    // Abono Cantabria
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), NfcReader.class);
                                    sepaloIntent.putExtra(NfcReader.DATA_TO_SEND, datos);
                                } else
                                {
                                    sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), BluetoothReader.class);                                	
                                    //sepaloIntent.setPackage("com.alsa.alsaopprivlib");
                                    //sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, "430010000000001203011100");
                                    sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                }
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this, sepaloIntent,1);

                            } else {
                                Log.d(LOGCATEGORY, "Sepalo BONOS servicio ya iniciado");                                                                
                                Sepalo.this.callbackContextConnect.success("\"\"");
                            }
                        } catch (Exception eEx) {
                            // TODO Auto-generated catch block
                            eEx.printStackTrace();              
                            status=false;  			
                            Sepalo.this.callbackContextConnect.error("[startOperationConsultaAb] Error: " + eEx.getMessage());
                        }
                    }
                });   
                return status;
            } catch (Exception err)
            {
                err.printStackTrace();
                callbackContextConnect.error("[execute] Error startOperationConsultaAb: " + err.getMessage());
                return false;
            }   
        } else if (action.equals("startPagoAbono")) 
        {
           try
           { 
                // Recarga Abono
                Log.d(LOGCATEGORY, "Sepalo BONOS startPagoAbono");
                this.callbackContextConnect = callbackContextConnect;
                
                this.datos="28"; // Pago Abono Cantabria               
                if (args != null) 
                {                    
                    tipoTarjeta = args.getString(0);                                        
                    this.datos += args.getString(1); // Ya se pasan todos los datos de la tarjeta en formato SEPALO
                }  else
                { // Faltan argumentos pago monedero
                // callbackContextError -> enviar mensaje de Error
                    Sepalo.this.callbackContextConnect.error("Es necesario información de la tarjeta");	
                }  
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {                           
                            if (Sepalo.this.reader == null) {
                                Log.d(LOGCATEGORY, "Sepalo BONOS iniciando conexión");

                                Intent sepaloIntent = null; 
                                sepaloIntent = new Intent(cordova.getActivity().getApplicationContext(), NfcReader.class);
                                //sepaloIntent.setPackage("com.alsa.alsaopprivlib");                                
                                sepaloIntent.putExtra(BluetoothReader.DATA_TO_SEND, datos);
                                cordova.startActivityForResult((CordovaPlugin)Sepalo.this, sepaloIntent,1);                                
                                
                            } else {
                                Log.d(LOGCATEGORY, "servicio ya iniciado");                                                                
                                Sepalo.this.callbackContextConnect.success("\"\"");
                            }
                        } catch (Exception eCAb) {
                            // TODO Auto-generated catch block
                            eCAb.printStackTrace();
                            status=false;
                            Sepalo.this.callbackContextConnect.error("[startCargaAbono] Error: " + eCAb.getMessage());                            
                        }
                    }
                });
                return status;
            } catch (Exception err)
            {
                err.printStackTrace();
                callbackContextConnect.error("[execute] Error startPagoAbono: " + err.getMessage());
                return false;
            }   
        } else {
            callbackContextConnect.error("Error Sepalo: opción no soportada -> " + action);
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
        	Log.d(LOGCATEGORY, "Sepalo BONOS onActivityResult");
        	
        	if (data==null)
        	{
        		callbackContextConnect.success("00999 Respuesta null del lector de tarjetas");
        	} else
        	{        	
	        	switch (requestCode) {
				case 1:
					if (resultCode == Activity.RESULT_OK) {
						String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);
						//Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_OK " + result, Toast.LENGTH_LONG).show();					
						String op= result.substring(0, 2);
						Integer code = Integer.valueOf(result.substring(2, 5));						
						if (code >0 )
						{
							result = getSepaloError(op, code);							
						}						
						callbackContextConnect.success(result);
						
					} else if (resultCode == Activity.RESULT_CANCELED) {
						String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);
						
						//Builder alertDialog = new AlertDialog.Builder(this);
						try {
							String op= result.substring(0, 2);
							Integer code = Integer.valueOf(result.substring(2, result.length()));
							//Integer code = Integer.valueOf(result.substring(2, 5));
							
							result = getSepaloError(op, code);
							
						} catch (Exception e) {
							result += " -> " + e.getMessage();						
						}					
						//Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_CANCELED " + result, Toast.LENGTH_LONG).show();					
						callbackContextConnect.success(result);
					}
					break;
				case 2:
					if (resultCode == Activity.RESULT_OK) {
						String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);					
						try {
							String op= result.substring(0, 2);					
							Integer code = Integer.valueOf(result.substring(2, 4));
							result = getSepaloError(op, code);							
						} catch (Exception e) {
							result += " -> " + e.getMessage();					
						}					
						//Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_CANCELED " + result, Toast.LENGTH_LONG).show();
						//this.callbackContextResult=this.cordova.getActivity().getApplicationContext();
						callbackContextConnect.success(result);
					} else if (resultCode == Activity.RESULT_CANCELED) {
						String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);
						//Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_CANCELED " + result, Toast.LENGTH_LONG).show();					
						callbackContextConnect.success(result);					
					}
					break;
				}
        	}
		} catch (Exception e) {
			// LOG.error("Error try to build the response message", e);
			Log.e(LOGCATEGORY, "error onActivityResult", e);
            e.printStackTrace();
			callbackContextConnect.error("Error al obtener la respuesta de Sepalo " + resultCode);
		}
    }
    
    private String getSepaloError (String op, int code)
    {
    	String result="";
    	try
    	{
	    	switch (code) {
	    	case 2:
				result = op + "002 Error al escribir en la tarjeta";							
				break;
			case 4:
				result = op + "004 Error al sacar la tarjeta antes de finalizar su lectura. Vuelva a pulsar el botón en la pantalla para validar la tarjeta ";							
				break;
			case 7:
				result = op + "007 Error en la lectura de la tarjeta ";							
				break;
			case 8:
				result = op + "008 Error en la lectura del num. de tarjeta ";							
				break;	
			case 9:
				result = op + "009 Tarjeta caducada ";							
				break;
			case 10:
				result = op + "010 Error de lectura de la tarjeta ";							
				break;
			case 11:
				result = op + "011 Tarjeta no soportada ";							
				break;
			case 12:
				result = op + "012 Error de lectura de la tarjeta ";							
				break;
			case 13:
				result = op + "013 Comprobar lector de tarjetas está vinculado a la tablet en los ajustes de Bluetooth";							
				break;
			case 14:							
				result = op + "014 No es posible conectar con el lector de tarjetas. Si no ve una luz azul intermitente, pulse botón de encendido en el lector de tarjetas y vuelva a seleccionar la tarjeta a validar";
				break;
			case 15:							
				result = op + "015 Compruebe que está habilitado el Bluetooth de la tablet";
				break;
			case 16:							
				result = op + "016 Superado tiempo para introducir tarjeta en el lector";
				break;							
			case 28:							
				result = op + "028 No hay saldo suficiente en la tarjeta monedero. Venta en efectivo";
				break;	
			case 17:							
				result = op + "017 El lector NFC del dispositivo no soporta la tecnología Mifare de la tarjeta de Cantabria";
				break;
			case 18:							
				result = op + "018 El dispositivo no soporta tecnología NFC";
				break;
			case 25:							
				result = op + "025 Problemas en la conexión NFC para realizar operaciones con la tarjeta de Cantabria";
				break;
			case 27:							
				result = op + "027 Problemas en la conexión NFC para realizar operaciones con la tarjeta de Cantabria";
				break;
			case 33:							
				result = op + "033 Error en la comunicación NFC para realizar operaciones con la tarjeta de Cantabria";
				break;
			case 42:							
				result = op + "042 Tarjeta en lista negra, no se puede operar con ella";
				break;	
			default:				
				//result = op + " Error: 0" + result.substring(2, 4);
				result = op + "0" + result.substring(2, 4) + " Error en la operación con el lector de tarjetas";
				break;
			}
    	
    	 
    	} catch (Exception e) {
			// LOG.error("Error try to build the response message", e);
			//Log.e(LOGCATEGORY, "error onActivityResult", e);
            e.printStackTrace();
            result = op + "000";
		}
    	
    	return result;  
    	
    }


}