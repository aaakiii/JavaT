package com.example.aki.climinal;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

/**
 * Created by AKI on 2017-06-01.
 */
public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdater;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mAdater = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdater);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;
        private boolean mRequiresPolice;

        public CrimeHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
        }
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Toast.makeText(getContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mRequiresPolice = mCrime.isRequiresPolice();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = null;
            if (viewType == 0) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_serious_crime, parent, false);
            } else {

                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crime, parent, false);
            }

            return new CrimeHolder(v);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            // assignment
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }
        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequiresPolice() == true)
                return 0;
            else
                return 1;
        }
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}