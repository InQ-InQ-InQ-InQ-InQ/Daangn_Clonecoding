package com.example.carrotmarket_cloneproject.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.carrotmarket_cloneproject.R;

public class HomeActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private NeighborhoodLifeFragment neighborhoodLifeFragment;
    private AroundMeFragment aroundMeFragment;
    private ChattingFragment chattingFragment;
    private MyCarrotFragment myCarrotFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        homeFragment = new HomeFragment();


    }

    public void frameOnClick(View view) {
        switch (view.getId()) {
            case R.id.llout_bottom_bar_home:
                moveHome();
                break;
            case R.id.llout_bottom_bar_neighborhood_life:
                moveNeighborhoodLife();
                break;
            case R.id.llout_bottom_bar_around_me:
                moveAroundMe();
                break;
            case R.id.llout_bottom_bar_chatting:
                moveChatting();
                break;
            case R.id.llout_bottom_bar_my_carrot:
                moveMyCarrot();
                break;
            default:
                break;
        }
    }

    public void moveHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
    }
    public void moveNeighborhoodLife() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, neighborhoodLifeFragment).commit();
    }
    public void moveAroundMe() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, aroundMeFragment).commit();
    }public void moveChatting() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, chattingFragment).commit();
    }
    public void moveMyCarrot() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, myCarrotFragment).commit();
    }

}