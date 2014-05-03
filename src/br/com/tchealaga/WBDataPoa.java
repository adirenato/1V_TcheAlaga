package br.com.tchealaga;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class WBDataPoa extends Activity {
	private String page;
	private ProgressDialog pd;
	private String URL_WS = "http://metroclima.procempa.com.br/dados.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wbdata_poa);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		// Progresso por tempo indeterminado.
		pd = null;
		pd = ProgressDialog.show(this, "Aguarde",
				"Sincronizando com WebService", true, false);

		// Inicia-se uma Thread para processar o webservice
		try {
			Thread thread = new Thread();
			thread.start();
		} catch (Exception e) {
			pd.dismiss();
			Log.e("WS", e.toString());
		}
		
		try {
			// Chama o método que fará o trabalho com o Json passando o endereço
			// do webservice.
			this.JSONFile(URL_WS);
		} catch (Exception e) {
			Log.e("WS", e.toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wbdata_poa, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_wbdata_poa,
					container, false);
			return rootView;
		}
	}

	private void JSONFile(String url) {
		try {

			// Le a pagina
			GetHttp http = new GetHttp(url);
			// page contem a string json
			page = http.page;

			// nativo do SDK da o parse da string json
			JSONObject object = (JSONObject) new JSONTokener(page).nextValue();

			// monta o array do retorno do json, lembrando que retorno é o PAI
			// do json impresso em page.
			// {"retorno":[
			// {"campo1":"valor1"},{"campo2":"valor2"},{"campo3":"valor3"} ]}
			JSONArray message = object.getJSONArray("retorno");

			// Percorre o array retornado
			// campo1, 2 e 3 são os nós da string json
			// {"campo1":"valor1"},{"campo2":"valor2"},{"campo3":"valor3"}
			for (int i = 0; i < message.length(); i++) {
				JSONObject lines = (JSONObject) new JSONTokener(
						message.getString(i)).nextValue();
				Log.d("WS", "[Retorno do WS]");
				Log.d("WS", "Retorno: " + lines.getString("campo1"));
				Log.d("WS", "Retorno: " + lines.getString("campo2"));
				Log.d("WS", "Retorno: " + lines.getString("campo3"));
				Log.d("WS", "[/Retorno do WS]");
			}

			Log.d("WS", "Sincronizado com sucesso");
			pd.dismiss();
		} catch (Exception e) {
			Log.e("WS", e.toString());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			// Chama o método que fará o trabalho com o Json passando o endereço
			// do webservice.
			this.JSONFile(URL_WS);
		} catch (Exception e) {
			Log.e("WS", e.toString());
		}
	}

}
