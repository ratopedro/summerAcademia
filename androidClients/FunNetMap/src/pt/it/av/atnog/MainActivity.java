package pt.it.av.atnog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import pt.it.av.atnog.map.MapsFragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public class MainActivity extends FragmentActivity {
	private String teamName = "Nome"; // Aten��o, o nome n�o pode ter espa�os
	private int icon = R.drawable.marker; // Nome do icon
	
	private MapsFragment map;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_LEFT_ICON); // Adi��o do icon � title bar
        setContentView(R.layout.activity_main);
        setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher); // Associa��o do icon com o recurso
        
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) != ConnectionResult.SUCCESS)
        {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Google Play Services were not detected, the application can't work correctly without them.")
				.setCancelable(false)
				.setTitle("Error")
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
			AlertDialog alert = builder.create();
			alert.show();
        }
        else
        {
	        // Check that the activity is using the layout version with
	        // the fragment_container FrameLayout
	        if (findViewById(R.id.fragment_container) != null) {
	
	            // However, if we're being restored from a previous state,
	            // then we don't need to do anything and should return or else
	            // we could end up with overlapping fragments.
	            if (savedInstanceState != null) {
	                return;
	            }
	
	            // Create an instance of ExampleFragment
	            map = new MapsFragment();
	            
	            Bundle bun = new Bundle();
	            
	            bun.putInt("icon", icon);
	            bun.putString("name", teamName);
	            
	            map.setArguments(bun);
	            
	            getSupportFragmentManager().beginTransaction()
	                    .add(R.id.fragment_container, map).commit();
        	}
        }
    }
}
