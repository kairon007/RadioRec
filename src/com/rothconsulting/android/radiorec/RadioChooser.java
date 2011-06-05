package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RadioChooser extends Activity {

	private final String TAG = this.getClass().getSimpleName();

	private void storePreferences() {
		SharedPreferences settings = getSharedPreferences(
				Constants.PREFERENCES_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Constants.SELECTED_STATION_ICON,
				RadioPlayer.SELECTED_STATION_ICON);
		editor.putString(Constants.SELECTED_STATION,
				RadioPlayer.SELECTED_STATION);
		editor.putString(Constants.SELECTED_STATION_STREAM,
				RadioPlayer.URL_LIVE_STREAM);
		editor.putString(Constants.SELECTED_STATION_HOMEPAGE,
				RadioPlayer.URL_HOMEPAGE);
		editor.putString(Constants.SELECTED_STATION_WEBCAM,
				RadioPlayer.URL_WEBCAM);
		editor.putString(Constants.SELECTED_STATION_CONTACT,
				RadioPlayer.URL_CONTACT);
		editor.commit();
	}

	private void finalTaks() {
		setResult(Activity.RESULT_OK, null);
		storePreferences();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooser);

		final Button zurueckButton = (Button) findViewById(R.id.buttonZurueck);
		zurueckButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		final ImageView imageViewRadio32 = (ImageView) findViewById(R.id.imageViewRadio32);
		imageViewRadio32.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio32_100x36;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_32);
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_32;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_32;
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_32;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_32;
				finalTaks();
			}
		});
		final ImageView imageViewRadio32Goldies = (ImageView) findViewById(R.id.imageViewRadio32Goldies);
		imageViewRadio32Goldies.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio32_goldies_42x40;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_32_goldies);
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_32_GOLDIES;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_32_GOLDIES;
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_32;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_32_GOLDIES;
				finalTaks();
			}
		});
		final ImageView imageViewRadio24 = (ImageView) findViewById(R.id.imageViewRadio24);
		imageViewRadio24.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio24;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_24;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_24;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_24);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_24;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_24;
				finalTaks();
			}
		});
		final ImageView imageViewRadio24Rock = (ImageView) findViewById(R.id.imageViewRadio24Rock);
		imageViewRadio24Rock.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio24_rock_45x38;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_24_ROCK;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_24;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_24_rock);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_24;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_24;
				finalTaks();
			}
		});
		final ImageView imageViewRadioEnergyBern = (ImageView) findViewById(R.id.imageViewRadioEnergyBern);
		imageViewRadioEnergyBern.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_energy_bern_75x36;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_ENERGY_BERN;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_ENERGY_BERN;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_energy_bern);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_ENERGY_BERN;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_ENERGY_BERN;
				finalTaks();
			}
		});
		final ImageView imageViewRadioEnergyZuerich = (ImageView) findViewById(R.id.imageViewRadioEnergyZurich);
		imageViewRadioEnergyZuerich
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_energy_zuerich_75x36;
						RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_ENERGY_ZUERICH;
						RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_ENERGY_ZUERICH;
						RadioPlayer.SELECTED_STATION = getString(R.string.radio_energy_zurich);
						RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_ENERGY_ZUERICH;
						RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_ENERGY_ZUERICH;
						finalTaks();
					}
				});
		final ImageView imageViewRadioEnergySwiss = (ImageView) findViewById(R.id.imageViewRadioEnergySwiss);
		imageViewRadioEnergySwiss
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_energy_swiss_75x36;
						RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_ENERGY_SWISS;
						RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_ENERGY_SWISS;
						RadioPlayer.SELECTED_STATION = getString(R.string.radio_energy_swiss);
						RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_ENERGY_SWISS;
						RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_ENERGY_SWISS;
						finalTaks();
					}
				});

		final ImageView imageViewRadioCapitalFM = (ImageView) findViewById(R.id.imageViewRadioCapitalFM);
		imageViewRadioCapitalFM.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_capital_fm_85x36;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_CAPITALFM;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_CAPITALFM;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_capital_fm);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_CAPITAL_FM;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_CAPITAL_FM;
				finalTaks();
			}
		});
		final ImageView imageViewRadioHoch2 = (ImageView) findViewById(R.id.imageViewRadioHoch2);
		imageViewRadioHoch2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_hoch2;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_HOCH2;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_HOCH2;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_hoch2);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_HOCH2;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_HOCH2;
				finalTaks();
			}
		});
		final ImageView imageViewRadioDRS1 = (ImageView) findViewById(R.id.imageViewRadioDRS1);
		imageViewRadioDRS1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_drs1;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_DRS1;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_DRS1;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_drs_1);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_DRS1;
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_DRS1;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_DRS1;
				finalTaks();
			}
		});
		final ImageView imageViewRadioDRS2 = (ImageView) findViewById(R.id.imageViewRadioDRS2);
		imageViewRadioDRS2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_drs2;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_DRS2;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_DRS2;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_drs_2);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_DRS2;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_DRS2;
				finalTaks();
			}
		});
		final ImageView imageViewRadioDRS3 = (ImageView) findViewById(R.id.imageViewRadioDRS3);
		imageViewRadioDRS3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_drs3;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_DRS3;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_DRS3;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_drs_3);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_DRS3;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_DRS3;
				finalTaks();
			}
		});
		final ImageView imageViewRadioDRS4News = (ImageView) findViewById(R.id.imageViewRadioDRS4News);
		imageViewRadioDRS4News.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_drs4news_121x36;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_DRS4;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_DRS4;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_drs_4);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_DRS4;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_DRS4;
				finalTaks();
			}
		});
		final ImageView imageViewRadioDRSVirus = (ImageView) findViewById(R.id.imageViewRadioDRSVirus);
		imageViewRadioDRSVirus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_drs_virus_135x36;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_DRS_VIRUS;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_DRS_VIRUS;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_drs_virus);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_DRS_VIRUS;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_DRS_VIRUS;
				finalTaks();
			}
		});
		final ImageView imageViewRadioRaBe = (ImageView) findViewById(R.id.imageViewRadioRaBe);
		imageViewRadioRaBe.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_rabe_36x36;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_RABE;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RABE;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_RaBe);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_RABE;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_RABE;
				finalTaks();
			}
		});
		final ImageView imageViewRadioSwissJazz = (ImageView) findViewById(R.id.imageViewRadioSwissJazz);
		imageViewRadioSwissJazz.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_swiss_jazz_120x30;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_SWISS_JAZZ;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_SWISS_JAZZ;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_swiss_jazz);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_SWISS_JAZZ;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_SWISS_JAZZ;
				finalTaks();
			}
		});
		final ImageView imageViewRadioSwissPop = (ImageView) findViewById(R.id.imageViewRadioSwissPop);
		imageViewRadioSwissPop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_swiss_pop_120x30;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_SWISS_POP;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_SWISS_POP;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_swiss_pop);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_SWISS_POP;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_SWISS_POP;
				finalTaks();
			}
		});
		final ImageView imageViewRadioSwissClassic = (ImageView) findViewById(R.id.imageViewRadioSwissClassic);
		imageViewRadioSwissClassic
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_swiss_classic_120x30;
						RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_SWISS_CLASSIC;
						RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_SWISS_CLASSIC;
						RadioPlayer.SELECTED_STATION = getString(R.string.radio_swiss_classic);
						RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_SWISS_CLASSIC;
						RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_SWISS_CLASSIC;
						finalTaks();
					}
				});
		final ImageView imageViewRadio1 = (ImageView) findViewById(R.id.imageViewRadio1);
		imageViewRadio1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio1_60x38;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_1;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_1;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_1);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_1;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_1;
				finalTaks();
			}
		});
		final ImageView imageViewRadioZuerisee = (ImageView) findViewById(R.id.imageViewRadioZuerisee);
		imageViewRadioZuerisee.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_zuerisee_88x40;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_ZUERISEE;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_ZUERISEE;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_zuerisee);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_ZUERISEE;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_ZUERISEE;
				finalTaks();
			}
		});
		final ImageView imageViewRadioPilatus = (ImageView) findViewById(R.id.imageViewRadioPilatus);
		imageViewRadioPilatus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_pilatus_80x40;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_PILATUS;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_PILATUS;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_pilatus);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_PILATUS;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_PILATUS;
				finalTaks();
			}
		});
		final ImageView imageViewRadioBeO = (ImageView) findViewById(R.id.imageViewRadioBeO);
		imageViewRadioBeO.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_beo_121x40;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_BEO;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_BEO;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_beo);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_BEO;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_BEO;
				finalTaks();
			}
		});
		final ImageView imageViewRadioTop = (ImageView) findViewById(R.id.imageViewRadioTop);
		imageViewRadioTop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_top_48x40;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_TOP;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_TOP;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_top);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_TOP;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_TOP;
				finalTaks();
			}
		});
		final ImageView imageViewRadioTopTwo = (ImageView) findViewById(R.id.imageViewRadioTopTwo);
		imageViewRadioTopTwo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_toptwo_46x40;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_TOP_TWO;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_TOP_TWO;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_top_two);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_TOP;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_TOP_TWO;
				finalTaks();
			}
		});
		final ImageView imageViewRadio20min = (ImageView) findViewById(R.id.imageViewRadio20min);
		imageViewRadio20min.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_20min_36x39;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_20MIN;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_20MIN;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_20min);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_20MIN;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_20MIN;
				finalTaks();
			}
		});
		final ImageView imageViewRadioNeo1 = (ImageView) findViewById(R.id.imageViewRadioNeo1);
		imageViewRadioNeo1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_neo1_88x36;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_NEO_1;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_NEO_1;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_neo1);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_NEO_1;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_NEO_1;
				finalTaks();
			}
		});
		final ImageView imageViewRadioNeoZwei = (ImageView) findViewById(R.id.imageViewRadioNeoZwei);
		imageViewRadioNeoZwei.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_neo_zwei_90x36;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_NEO_ZWEI;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_NEO_ZWEI;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_neozwei);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_NEO_1;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_NEO_ZWEI;
				finalTaks();
			}
		});
		final ImageView imageViewRadioBasel = (ImageView) findViewById(R.id.imageViewRadioBasel);
		imageViewRadioBasel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_basel_81x36;
				RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_BASEL;
				RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_BASEL;
				RadioPlayer.SELECTED_STATION = getString(R.string.radio_basel);
				RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_BASEL;
				RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_BASEL;
				finalTaks();
			}
		});
		// Shoutcast Streams gehen erst ab Android 2.2 (Level 8)
		if (Build.VERSION.SDK_INT >= 8) {
			final ImageView imageViewRadioArgovia = (ImageView) findViewById(R.id.imageViewRadioArgovia);
			imageViewRadioArgovia
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_argovia_40x40;
							RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_ARGOVIA;
							RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_ARGOVIA;
							RadioPlayer.SELECTED_STATION = getString(R.string.radio_argovia);
							RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_ARGOVIA;
							RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_ARGOVIA;
							finalTaks();
						}
					});
			final ImageView imageViewRadioArgoviaHitmix = (ImageView) findViewById(R.id.imageViewRadioArgoviaHitmix);
			imageViewRadioArgoviaHitmix
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_argovia_40x40;
							RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_ARGOVIA_HITMIX;
							RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_ARGOVIA;
							RadioPlayer.SELECTED_STATION = getString(R.string.radio_argovia_hitmix);
							RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_ARGOVIA;
							RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_ARGOVIA;
							finalTaks();
						}
					});
			final ImageView imageViewRadioArgoviaClassicRock = (ImageView) findViewById(R.id.imageViewRadioArgoviaClassicRock);
			imageViewRadioArgoviaClassicRock
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_argovia_40x40;
							RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_ARGOVIA_CLASSIC_ROCK;
							RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_ARGOVIA;
							RadioPlayer.SELECTED_STATION = getString(R.string.radio_argovia_classic_rock);
							RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_ARGOVIA;
							RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_ARGOVIA;
							finalTaks();
						}
					});
			final ImageView imageViewRadio105 = (ImageView) findViewById(R.id.imageViewRadio105);
			imageViewRadio105.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					RadioPlayer.SELECTED_STATION_ICON = R.drawable.logo_radio_105_60x40;
					RadioPlayer.URL_LIVE_STREAM = Constants.URL_LIVE_STREAM_RADIO_105;
					RadioPlayer.URL_HOMEPAGE = Constants.URL_HOMEPAGE_RADIO_105;
					RadioPlayer.SELECTED_STATION = getString(R.string.radio_105);
					RadioPlayer.URL_WEBCAM = Constants.URL_WEBCAM_RADIO_105;
					RadioPlayer.URL_CONTACT = Constants.URL_CONTACT_RADIO_105;
					finalTaks();
				}
			});
		}

	}

	// ------------------------------------------------------------
	// Menu Stuff
	// ------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.infomenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.zurueck:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
