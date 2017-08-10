package com.chichkanov.yandex_weather.ui.change_city;

import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangeCityPresenterTest {

/*
    private String DEFAULT_CITY = "Moscow";

    @Mock
    ChangeCityInteractor changeCityInteractor;

    @Mock
    ChangeCityView changeCityView;

    private NavigationManager navigationManager;


    private TestScheduler testScheduler = new TestScheduler();

    @InjectMocks
    ChangeCityPresenter presenter = new ChangeCityPresenter(changeCityInteractor, testScheduler,
            testScheduler);

    private String json = "{\"predictions\":[{\"description\":\"Moscow, Russia\",\"id\":\"1a0f08fcbc047354782f00ab52e66fb56d1aadf7\",\"matched_substrings\":[{\"length\":3,\"offset\":0}],\"place_id\":\"ChIJybDUc_xKtUYRTM9XV8zWRD0\",\"reference\":\"CjQmAAAA4Yyc3PS7fhSUWaT6zyDF1YmYs22SLZO-6nCGB07Ao89dqHM1P7ocWV9_lx76iNLCEhCfszY-DMEKXlk1Sj9DnZd-GhSgeA_VWLL3z1p6_oJx2T-uQx2_FQ\",\"structured_formatting\":{\"main_text\":\"Moscow\",\"main_text_matched_substrings\":[{\"length\":3,\"offset\":0}],\"secondary_text\":\"Russia\"},\"terms\":[{\"offset\":0,\"value\":\"Moscow\"},{\"offset\":8,\"value\":\"Russia\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Móstoles, Spain\",\"id\":\"7277956a0ecb371ab26b2c7249b74dfab45f125e\",\"matched_substrings\":[{\"length\":3,\"offset\":0}],\"place_id\":\"ChIJN8KDzdWNQQ0RkhLXONCLKRw\",\"reference\":\"CjQoAAAA582Xwc9YdcIb8gJUrX7aPW-oXG7Oq1lqlKPjJu3h02RQQyus0Srg8d8p8CYem9xsEhCi2y6zRrIELjX0zkzqP10oGhT4-n_pKtdZPyZ6LDqna0Ie4iHMjg\",\"structured_formatting\":{\"main_text\":\"Móstoles\",\"main_text_matched_substrings\":[{\"length\":3,\"offset\":0}],\"secondary_text\":\"Spain\"},\"terms\":[{\"offset\":0,\"value\":\"Móstoles\"},{\"offset\":10,\"value\":\"Spain\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Mosman, New South Wales, Australia\",\"id\":\"a4598119b3d8d58e3624f83d2387442f905d9aae\",\"matched_substrings\":[{\"length\":3,\"offset\":0}],\"place_id\":\"ChIJo_-SrTusEmsREMIyFmh9AQU\",\"reference\":\"CkQ6AAAAIgynMwq1fj07DoUnVwaFhre2SleASd9pgIjIqGwwIBSUMer7kiOzUF_XBWex_jPafJxdErefnmZyWgSro9BxmRIQR2seGvtS7dA3W8bIBzFcfhoUOY1P85MBZKgLk8Wn8Kg8vsHT_zs\",\"structured_formatting\":{\"main_text\":\"Mosman\",\"main_text_matched_substrings\":[{\"length\":3,\"offset\":0}],\"secondary_text\":\"New South Wales, Australia\"},\"terms\":[{\"offset\":0,\"value\":\"Mosman\"},{\"offset\":8,\"value\":\"New South Wales\"},{\"offset\":25,\"value\":\"Australia\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Mostar, Federation of Bosnia and Herzegovina, Bosnia and Herzegovina\",\"id\":\"07dc47071701780220b81e0e43c7d8a1418cba04\",\"matched_substrings\":[{\"length\":3,\"offset\":0}],\"place_id\":\"ChIJqUBjO6RDSxMRWnzj1LIyTwE\",\"reference\":\"CmRcAAAAqbbP5PuVBQvc4xbpLzoOm1ZQBxrN3uoOunoTrsbP6Zq-XNloYYJK5v5g9zIuDrlRn3AHs5CzGiHlQYfJUWfFCe0Sn0_Iit0COk5RyDEwj7QlyqcjocJd82nPUlWBTgSnEhB_g76nfOHYmIQ_U80TO8lUGhRBGmyxHeEtSL9zZUjoeS3rqvakfA\",\"structured_formatting\":{\"main_text\":\"Mostar\",\"main_text_matched_substrings\":[{\"length\":3,\"offset\":0}],\"secondary_text\":\"Federation of Bosnia and Herzegovina, Bosnia and Herzegovina\"},\"terms\":[{\"offset\":0,\"value\":\"Mostar\"},{\"offset\":8,\"value\":\"Federation of Bosnia and Herzegovina\"},{\"offset\":46,\"value\":\"Bosnia and Herzegovina\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Moses Lake, WA, United States\",\"id\":\"2f4e0fd51b9ca0e5d3a506f013283de8db1cec54\",\"matched_substrings\":[{\"length\":3,\"offset\":0}],\"place_id\":\"ChIJ27vfjjU_mVQRJCDl7OXrUD8\",\"reference\":\"CkQ1AAAAUm1w5gy7a7ypL1-TYQVd2OcsXrbPnatjv83QN1PRbkrdimFig3-DRgSwPcvr9GlI8RF3OqqXNOMPCHPltp-eXhIQoisG1G1cQtB9RPZErFtw4xoUEXKROs4G3faLUatlW-Cg1hw4z0M\",\"structured_formatting\":{\"main_text\":\"Moses Lake\",\"main_text_matched_substrings\":[{\"length\":3,\"offset\":0}],\"secondary_text\":\"WA, United States\"},\"terms\":[{\"offset\":0,\"value\":\"Moses Lake\"},{\"offset\":12,\"value\":\"WA\"},{\"offset\":16,\"value\":\"United States\"}],\"types\":[\"locality\",\"political\",\"geocode\"]}],\"status\":\"OK\"}";




    @Before
    public void setUp() {

        navigationManager = Mockito.mock(NavigationManager.class);

    }


    @Test
    public void testAttachView() throws Exception {
        when(changeCityInteractor.getCurrentCity()).thenReturn(DEFAULT_CITY);
        presenter.attachView(changeCityView);
        verify(changeCityView, times(1)).showCurrentCity(DEFAULT_CITY);
        verifyNoMoreInteractions(changeCityView);
    }

    @Test
    public void testAttachViewAfterRecreatingView() throws Exception {

        when(changeCityInteractor.getCitySuggestion("Mos"))
                .thenReturn(Observable.just(new Gson().fromJson(json, CitySuggestion.class)));
        presenter.loadCitySuggestion("Mos");
        presenter.attachView(changeCityView);
        verify(changeCityView, times(0)).showCurrentCity(DEFAULT_CITY);
        verify(changeCityView, times(1)).showCurrentCity("Mos");
    }

    @Test
    public void testOnClearClick() {
        presenter.attachView(changeCityView);
        presenter.onClearClick();
        verify(changeCityView, times(1)).clearInput();
        verify(changeCityView, times(1)).hideSuggestionList();
    }

    @Test
    public void testLoadCitySuggestionError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));
        when(changeCityInteractor.getCitySuggestion(DEFAULT_CITY))
                .thenReturn(Observable.error(nothing));
        presenter.attachView(changeCityView);
        presenter.loadCitySuggestion(DEFAULT_CITY);
        testScheduler.triggerActions();
        verify(changeCityView, times(1)).showError();
    }



    @Test
    public void testLoadCitySuggestionSuccess() {
        CitySuggestion citySuggestion = new Gson().fromJson(json, CitySuggestion.class);
        when(changeCityInteractor.getCitySuggestion(DEFAULT_CITY))
                .thenReturn(Observable.just(citySuggestion));
        presenter.attachView(changeCityView);
        presenter.loadCitySuggestion(DEFAULT_CITY);
        testScheduler.triggerActions();
        verify(changeCityView, times(1)).showSuggestions(citySuggestion.getPredictions());
        verify(changeCityView, times(1)).showSuggestionList();
    }

    @Test
    public void testLoadCitySuggestionWithNullOrEmptyCity() {
        presenter.attachView(changeCityView);
        presenter.loadCitySuggestion(null);
        presenter.loadCitySuggestion("");
        testScheduler.triggerActions();
        verify(changeCityView, times(2)).hideClearButton();
    }

    @Test
    public void testSetObservableEditText() {
        CitySuggestion citySuggestion = new Gson().fromJson(json, CitySuggestion.class);
        when(changeCityInteractor.getCitySuggestion(DEFAULT_CITY))
                .thenReturn(Observable.just(citySuggestion));
        presenter.attachView(changeCityView);
        presenter.setObservable(Observable.just(new StringBuilder().append(DEFAULT_CITY)));
        testScheduler.triggerActions();
        verify(changeCityView, times(1)).showSuggestions(citySuggestion.getPredictions());
        verify(changeCityView, times(1)).showSuggestionList();
    }

    @Test
    public void testOnCurrentCityChanged() {
        doThrow().doNothing().when(changeCityInteractor).setCurrentCity(DEFAULT_CITY);
        presenter.attachView(changeCityView);
        presenter.addNavigationManager(navigationManager);
        presenter.onCurrentCityChanged(DEFAULT_CITY);
        verify(navigationManager, times(1)).navigateTo(any());
        verify(changeCityInteractor, times(1)).setCurrentCity(DEFAULT_CITY);
        verifyNoMoreInteractions(navigationManager);

    }

    @Test
    public void testDetachView() {
        presenter.detachView(changeCityView);
        verifyZeroInteractions(changeCityView);
    }

    @Test
    public void testAddNullNavigationManagerAndCallOnCurrentCityChanged() {
        doThrow().doNothing().when(changeCityInteractor).setCurrentCity(DEFAULT_CITY);
        presenter.attachView(changeCityView);
        presenter.addNavigationManager(null);
        presenter.onCurrentCityChanged(DEFAULT_CITY);
        verifyZeroInteractions(navigationManager);
    }

*/
}