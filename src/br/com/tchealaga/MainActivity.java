package br.com.tchealaga;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			} else {
				//Pega a posição de Porto Alegre
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(-30.0446934, -51.1818218)).zoom(12)
						.build();

				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
}
