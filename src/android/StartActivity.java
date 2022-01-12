package cordova.plugin;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.cordova.CallbackContext;

import org.json.JSONException;
import org.json.JSONObject;

//import com.alsa.alsaconnect.R;
import com.alsa.alsaopprivlib.BluetoothReader;
//import es.tecnova.alsa.R;
//import com.alsa.alsaopprivlib.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Sepalo {

	private InputStream mInput;
	private OutputStream mOutput;
	
	private CallbackContext callbackContextResult;

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		// get Button and editText
		purchaseButton = (Button) findViewById(R.id.purchase);

		cancelButton = (Button) findViewById(R.id.cancel);

		// disabledPaymentScreen();
		// reader = new BluetoothReaderImpl(receiverHandler, mInput, mOutput);
	}*/

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			Log.d(LOGCATEGORY, "Sepalo onActivityResult");
			switch (requestCode) {
			case 1:
				if (resultCode == Activity.RESULT_OK) {
					String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);
					Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_OK" + result, Toast.LENGTH_LONG).show();
					//this.callbackContextResult=this.cordova.getActivity().getApplicationContext();
					super.callbackContextConnect.success(result);
					//Builder alertDialog = new AlertDialog.Builder(this);
					//alertDialog.setMessage(result);
					//alertDialog.show();
				} else if (resultCode == Activity.RESULT_CANCELED) {
					String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);
					
					//Builder alertDialog = new AlertDialog.Builder(this);
					try {
						Integer code = Integer.valueOf(result.substring(2, 4));
						switch (code) {
						case 13:
							result += " -> " + code;
							//alertDialog.setMessage(getResources().getString(R.string.msgPairDevice));
							break;
						case 14:
							//alertDialog.setMessage(getResources().getString(R.string.msgCreateConnect));
							result += " -> " + code;
							break;
						case 15:
							//alertDialog.setMessage(getResources().getString(R.string.msgStarDevice));
							result += " -> " + code;
							break;
						default:
							//alertDialog.setMessage(result);
							result += " -> " + code;
							break;
						}
					} catch (Exception e) {
						result += " -> " + e.getMessage();
						//alertDialog.setMessage(result);
					}
					//alertDialog.show();
					Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_CANCELED " + result, Toast.LENGTH_LONG).show();
					//this.callbackContextResult=this.cordova.getActivity().getApplicationContext();
					super.callbackContextConnect.success(result);
				}
				break;
			case 2:
				if (resultCode == Activity.RESULT_OK) {
					String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);
					//Builder alertDialog = new AlertDialog.Builder(this);
					try {
						Integer code = Integer.valueOf(result.substring(2, 4));
						switch (code) {
						case 13:
							//alertDialog.setMessage(getResources().getString(R.string.msgPairDevice));
							result += " -> " + code;
							break;
						case 14:
							//alertDialog.setMessage(getResources().getString(R.string.msgCreateConnect));
							result += " -> " + code;
							break;
						case 15:
							//alertDialog.setMessage(getResources().getString(R.string.msgStarDevice));
							result += " -> " + code;
							break;
						default:
							//alertDialog.setMessage(result);
							result += " -> " + code;
							break;
						}
					} catch (Exception e) {
						result += " -> " + e.getMessage();
						//alertDialog.setMessage(result);
					}
					//alertDialog.show();
					Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_CANCELED " + result, Toast.LENGTH_LONG).show();
					//this.callbackContextResult=this.cordova.getActivity().getApplicationContext();
					super.callbackContextConnect.success(result);
				} else if (resultCode == Activity.RESULT_CANCELED) {
					String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);
					Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_CANCELED " + result, Toast.LENGTH_LONG).show();
					//this.callbackContextResult=this.cordova.getActivity().getApplicationContext();
					super.callbackContextConnect.success(result);
					//Builder alertDialog = new AlertDialog.Builder(this);
					//alertDialog.setMessage(result);
					//alertDialog.show();
				}
				break;
			}
		} catch (Exception e) {
			// LOG.error("Error try to build the response message", e);
			super.callbackContextConnect.error("Error al obtener la respuesta de Sepalo");
		}
		
		/*try {
			switch (requestCode) {
			case 1:
				if (resultCode == Activity.RESULT_OK) {

					String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);
					
					Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_OK", Toast.LENGTH_LONG).show();
					//this.callbackContextResult=this.cordova.getActivity().getApplicationContext();
					super.callbackContextConnect.success(result);					
				} else if (resultCode == Activity.RESULT_CANCELED) {
					String result = data.getExtras().getString(BluetoothReader.RESULT_DATA);
					Toast.makeText(this.cordova.getActivity().getApplicationContext(), "RESULT_DATA", Toast.LENGTH_LONG).show();
					super.callbackContextConnect.success(result);
				}
				break;
			}
		} catch (Exception e) {
			// LOG.error("Error try to build the response message", e);
			super.callbackContextConnect.error("Error al obtener la respuesta de Sepalo");
		}*/
	}

}