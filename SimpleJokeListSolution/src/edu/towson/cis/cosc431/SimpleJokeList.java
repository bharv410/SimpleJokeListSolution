package edu.towson.cis.cosc431;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleJokeList extends Activity {

	/** Contains the list Jokes the Activity will present to the user **/
	protected ArrayList<Joke> m_arrJokeList;

	/**
	 * LinearLayout used for maintaining a list of Views that each display Jokes
	 **/
	protected LinearLayout m_vwJokeLayout;

	/**
	 * EditText used for entering text for a new Joke to be added to
	 * m_arrJokeList.
	 **/
	protected EditText m_vwJokeEditText;

	/**
	 * Button used for creating and adding a new Joke to m_arrJokeList using the
	 * text entered in m_vwJokeEditText.
	 **/
	protected Button m_vwJokeButton;
	
	/**
	 * Background Color values used for alternating between light and dark rows
	 * of Jokes.  
	 */
	
	protected boolean m_nDarkColor = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initLayout();
		initAddJokeListeners();
		
		Resources res = this.getResources();
		String[] jokes = res.getStringArray(R.array.jokeList);
		
		m_arrJokeList = new ArrayList<Joke>();
		
		for(int i = 0; i < jokes.length; i++)
			addJoke(jokes[i]);
	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets the Layout
	 * for this Activity. 
	 */
	protected void initLayout() {
		m_vwJokeButton = (Button) this.findViewById(R.id.addJokeButton);
		m_vwJokeEditText = (EditText) this.findViewById(R.id.newJokeEditText);
		m_vwJokeLayout = (LinearLayout) this.findViewById(R.id.jokeListViewGroup);
	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets the Event
	 * Listeners which will respond to requests to "Add" a new Joke to the 
	 * list. 
	 */
	protected void initAddJokeListeners() {
		m_vwJokeButton.setOnClickListener(new OnClickListener() {			 
			@Override
			 public void onClick(View view) {
			 
				if (!m_vwJokeEditText.getText().toString().equals("")) {
					addJoke(m_vwJokeEditText.getText().toString());
					m_vwJokeEditText.setText("");
				}
			   
				InputMethodManager imm = (InputMethodManager)
				  getSystemService(Context.INPUT_METHOD_SERVICE);

				imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(), 0);

			 } 
			});
				
		m_vwJokeEditText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int arg1, KeyEvent arg2) {
				 if ((arg1 == KeyEvent.KEYCODE_ENTER) || (arg1 == KeyEvent.KEYCODE_DPAD_CENTER)) {
					 if (!m_vwJokeEditText.getText().toString().equals("")) {
							addJoke(m_vwJokeEditText.getText().toString());
							m_vwJokeEditText.setText("");
						}
				 }
				
				return true;
			}
	
		});
			
		//make the keyboard disappear when focus on text field  
		InputMethodManager imm = (InputMethodManager)
		  getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(), 0);
	}

	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to m_arrJokeList, and display it on screen.
	 * 
	 * @param strJoke
	 *            A string containing the text of the Joke to add.
	 */
	protected void addJoke(String strJoke) {
		Joke newJoke = new Joke(strJoke);
		m_arrJokeList.add(newJoke);
		
		TextView newJokeTextView = new TextView(this);
		newJokeTextView.setText(strJoke);
		
		if(this.m_nDarkColor) {
			newJokeTextView.setBackgroundColor(getResources().getColor(R.color.dark));
			this.m_nDarkColor = false;
		}	
		else {
			newJokeTextView.setBackgroundColor(getResources().getColor(R.color.light));
			this.m_nDarkColor = true;
		}
		m_vwJokeLayout.addView(newJokeTextView);		
	}
}