package com.androidthermostat.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidthermostat.client.data.Servers;
import com.androidthermostat.client.data.Settings;

public class GeneralSettingsFragment extends SherlockFragment {

	EditText nameText;
    EditText openWeatherMapStation;
    EditText ApiKeyText;
	EditText passwordText;
	EditText forecastUrlText;
	RadioButton scaleC;
	RadioButton scaleF;
    RadioButton time12;
    RadioButton time24;


	View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.general_settings, null);
		
		nameText = (EditText) root.findViewById(R.id.nameText);
        openWeatherMapStation = (EditText) root.findViewById(R.id.openWeatherMapStation);
        ApiKeyText = (EditText) root.findViewById(R.id.ApiKeyText);
		passwordText = (EditText) root.findViewById(R.id.passwordText);
		forecastUrlText = (EditText) root.findViewById(R.id.forecastUrlText);
		scaleC = (RadioButton) root.findViewById(R.id.scaleC);
		scaleF = (RadioButton) root.findViewById(R.id.scaleF);
        time12 = (RadioButton) root.findViewById(R.id.time12);
        time24 = (RadioButton) root.findViewById(R.id.time24);
		
		Settings s = Settings.getCurrent();
		
		nameText.setText( s.getName() );
        openWeatherMapStation.setText( String.valueOf(s.getOpenWeatherMapStation()) );
        ApiKeyText.setText( s.getApiKey() );
		passwordText.setText( String.valueOf(s.getPassword()) );
		forecastUrlText.setText( s.getForecastUrl() );
		
		if (s.getDisplayCelsius()) scaleC.setChecked(true); else scaleF.setChecked(true);

        if (s.getDisplayTime24h()) time24.setChecked(true); else time12.setChecked(true);

		
		return root;
	}
	

	@Override
	public void onPause()
	{
		new Thread(new Runnable() {
		    public void run() {
		    	saveData();
		    }
		  }).start();
	    
	    super.onPause();
	}
	
	//onPause
	private void saveData()
	{
		Settings s = Settings.getCurrent();
		s.setName(nameText.getText().toString());
        s.setOpenWeatherMapStation( Integer.parseInt(openWeatherMapStation.getText().toString()) );
        s.setApiKey(ApiKeyText.getText().toString());
		s.setForecastUrl ( forecastUrlText.getText().toString());
		s.setPassword(passwordText.getText().toString());
		s.setDisplayCelsius(scaleC.isChecked());
        s.setDisplayTime24h(time24.isChecked());
		s.save();
		
		Servers.getCurrent().getSelectedServer().setPassword(s.getPassword());
		
	}
	
}
