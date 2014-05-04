package br.com.tchealaga;

import android.location.LocationListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	// Google Map
	private GoogleMap googleMap;

	Marker startPerc;

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

	private LocationManager retornaLocationManager() {
		LocationManager lc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		return lc;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		retornaLocationManager().removeUpdates((LocationListener) this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "Rua");
		menu.add(0, 1, 1, "Satélite");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureID, MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
			break;
		case 1:
			googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
			break;
		default:
			googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
			break;

		}
		return true;
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

				
				if (!retornaLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					Toast.makeText(this, "Sinal GPS não encontrado",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(
							Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(intent);
				}
				
				googleMap.setMyLocationEnabled(true);
				retornaLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
				
				/*
				 Location location = retornaLocationManager()					
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				
				
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(location.getLatitude(), location
								.getLongitude())).zoom(12).build();

				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				
				
				// Initialize the location fields
				if (location != null) {
					Toast.makeText(
							this,
							"Selected Provider " + LocationManager.GPS_PROVIDER,
							Toast.LENGTH_SHORT).show();
					onLocationChanged(location);
					
				} else {

					// do something
				}*/

			}
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		//if (location != null) {
			//if (startPerc != null)
			///	startPerc.remove();

			double lat = location.getLatitude();
			double lng = location.getLongitude();
			
			LatLng coordinate = new LatLng(lat, lng);
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(coordinate).zoom(21).build();
			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

	
			
			/*startPerc = googleMap.addMarker(new MarkerOptions()
					.position(coordinate)
					.title("Start")
					.snippet("Começo do percuso")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));*/
			// .fromResource(R.drawable.ic_launcher)));
		//}
		// if(userMarker!=null) userMarker.remove();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}
