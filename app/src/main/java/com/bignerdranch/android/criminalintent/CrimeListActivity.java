package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeUpdated(Crime crime){
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }


    @Override
    public void onCrimeSelected(Crime crime){
        if (findViewById(R.id.detail_fragment_container) == null){
            Intent intent = CrimePagerActivity.newIntent(this,crime.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }

    @Override
    public void onDeleteCrime(Crime crime){

        if (findViewById(R.id.detail_fragment_container) == null){
            //Code for deleting in mobile phones
        } else {
            //Code for deleting in tablets
            CrimeLab.get(getApplicationContext()).deleteCrime(crime);

            //Detaching fragment
            Fragment detail = getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
            getSupportFragmentManager().beginTransaction().remove(detail).commit();

            //Updating UI
            CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            listFragment.updateUI();
        }
    }
}
