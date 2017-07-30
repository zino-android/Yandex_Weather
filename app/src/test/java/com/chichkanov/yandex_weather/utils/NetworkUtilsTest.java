package com.chichkanov.yandex_weather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilsTest {


    @Mock
    Context fakeContext;
    @Mock
    ConnectivityManager fakeConnectivityManager;
    @Mock
    NetworkInfo fakeNetworkInfo;


    @Before
    public void setUp() {
        Mockito.when(fakeContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(fakeConnectivityManager);

    }

    @Test
    public void testIsNotConnected() throws Exception {
        Mockito.when(fakeConnectivityManager.getActiveNetworkInfo()).thenReturn(null);
        boolean isConnected = NetworkUtils.isConnected(fakeContext);
        assertFalse(isConnected);
        Mockito.when(fakeConnectivityManager.getActiveNetworkInfo()).thenReturn(fakeNetworkInfo);
        Mockito.when(fakeNetworkInfo.isConnectedOrConnecting()).thenReturn(false);
        isConnected = NetworkUtils.isConnected(fakeContext);
        assertFalse(isConnected);
    }

    @Test
    public void testIsConnected() {
        Mockito.when(fakeConnectivityManager.getActiveNetworkInfo()).thenReturn(fakeNetworkInfo);
        Mockito.when(fakeNetworkInfo.isConnectedOrConnecting()).thenReturn(true);
        boolean isConnected = NetworkUtils.isConnected(fakeContext);
        assertTrue(isConnected);
    }


}