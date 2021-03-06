package com.mm.caju;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mm.caju.caju_seqMdl.MiscMovement;
import com.mm.caju.caju_seqMdl.Movement;
import com.mm.caju.caju_seqMdl.TimeSlot;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SequenceElementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SequenceElementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SequenceElementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TimeSlot tsl = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SequenceElementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SequenceElementFragment newInstance(String param1, String param2) {
        SequenceElementFragment fragment = new SequenceElementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SequenceElementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sequence_element, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // time
        TextView timeView = (TextView) this.getView().findViewById(R.id.textView_time);
        timeView.setText( Integer.toString(tsl.getTime()) );

        ////
        // top player mov
        CajuMovIconView topMovIconView = (CajuMovIconView) this.getView().findViewById(R.id.imageView_player_top);
        EditText topMovNoteView = (EditText) this.getView().findViewById(R.id.editText_note_top);

        // insert "continue mov" in case of unset mov
        if (tsl.getTopPlayerMov() == null ){
            MiscMovement cont = new MiscMovement();
            cont.setMovName("...");
            cont.setMovIconID(R.mipmap.ic_mov_cont);
            tsl.setTopPlayerMov( cont );
        }

        topMovIconView.setTimeslot( tsl );
        topMovIconView.setImageDrawable( getResources().getDrawable( tsl.getTopPlayerMov().getMovIconID() ) );
        topMovIconView.setOnDragListener( new MovDragListener() );

        topMovNoteView.setText( tsl.getTopPlayerMov().getMovNote() );
        topMovNoteView.setHint( tsl.getTopPlayerMov().getMovName() );
        topMovNoteView.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                getTsl().getTopPlayerMov().setMovNote( s.toString() );
            }
        });

        ////
        // bot player mov
        CajuMovIconView botMovIconView = (CajuMovIconView) this.getView().findViewById(R.id.imageView_player_bot);
        EditText botMovNoteView = (EditText) this.getView().findViewById(R.id.editText_note_bot);

        // insert "continue mov" in case of unset mov
        if (tsl.getBotPlayerMov() == null ) {
            MiscMovement cont = new MiscMovement();
            cont.setMovName("...");
            cont.setMovIconID(R.mipmap.ic_mov_cont);
            tsl.setBotPlayerMov( cont );
        }

        botMovIconView.setTimeslot( tsl );
        botMovIconView.setImageDrawable( getResources().getDrawable( tsl.getBotPlayerMov().getMovIconID() ) );
        botMovIconView.setOnDragListener(new MovDragListener());

        botMovNoteView.setText( tsl.getBotPlayerMov().getMovNote() );
        botMovNoteView.setHint( tsl.getBotPlayerMov().getMovName() );
        botMovNoteView.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                getTsl().getBotPlayerMov().setMovNote( s.toString() );
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSeqElementFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
    * Getters and Setters
    */
    public TimeSlot getTsl() {
        return tsl;
    }

    public void setTsl(TimeSlot tsl) {
        this.tsl = tsl;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onSeqElementFragmentInteraction(Uri uri);
    }



    /**
     * This
     *
     */
    private class MovDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // indicate valid drop zone
                    v.setBackgroundColor( Color.DKGRAY );
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(Color.TRANSPARENT);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped,
                    // insert passed movement object
                    CajuMovIconView icVw = (CajuMovIconView) v;
                    if (icVw.equals( getView().findViewById(R.id.imageView_player_bot)) ){
                        Movement mov = (Movement) event.getLocalState();
                        try {
                            icVw.getTimeslot().setBotPlayerMov( (Movement) mov.clone());
                            icVw.setBackgroundColor(Color.TRANSPARENT);
                            icVw.setImageDrawable(getResources().getDrawable( icVw.getTimeslot().getBotPlayerMov().getMovIconID()));
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (icVw.equals( getView().findViewById(R.id.imageView_player_top)) ){
                        Movement mov = (Movement) event.getLocalState();
                        try {
                            icVw.getTimeslot().setTopPlayerMov((Movement) mov.clone());
                            icVw.setBackgroundColor(Color.TRANSPARENT);
                            icVw.setImageDrawable(getResources().getDrawable(icVw.getTimeslot().getTopPlayerMov().getMovIconID()));
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.TRANSPARENT);
                default:
                    break;
            }
            return true;
        }
    }
}
