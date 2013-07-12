package pt.it.av.atnog;

import pt.it.av.atnog.message.MessageFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public class MainActivity extends FragmentActivity {
	private String teamName = "Nome"; // Atenção, o nome não pode ter espaços

	private MessageFragment messenger;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.activity_main);
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher);

		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			}

			Bundle bun = new Bundle();
			bun.putString("name", teamName);
			messenger = new MessageFragment();
			messenger.setArguments(bun);

			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, messenger).commit();
		}
	}

	public void sendMessage(View view) {
		messenger.sendMessage(view);
	}

}
