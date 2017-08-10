package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.api.PlacesApi;
import com.chichkanov.yandex_weather.db.WeatherDatabase;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.model.places.Prediction;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.HttpException;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class CityRepositoryTest {
    @Mock
    private PlacesApi placesApi;
    @Mock
    private WeatherDatabase database;

    @InjectMocks
    private CityRepositoryImpl cityRepository;

    String json = "{\"predictions\":[{\"description\":\"Москва, Россия\",\"id\":\"1a0f08fcbc047354782f00ab52e66fb56d1aadf7\",\"matched_substrings\":[{\"length\":6,\"offset\":0}],\"place_id\":\"ChIJybDUc_xKtUYRTM9XV8zWRD0\",\"reference\":\"CkQyAAAAqvB1kRNhT-EkP7I8STxOKCLzQECBLP_UMvYUl0GiEsDuEwMZ_gf70cokRMp7FpICfC0R2Grx1xxmh0rqqx_qOBIQKYEzoaK3q3V-jJ1tQq62HBoURtd0JLtQ68DYz9XMs-OMxfHuTK8\",\"structured_formatting\":{\"main_text\":\"Москва\",\"main_text_matched_substrings\":[{\"length\":6,\"offset\":0}],\"secondary_text\":\"Россия\"},\"terms\":[{\"offset\":0,\"value\":\"Москва\"},{\"offset\":8,\"value\":\"Россия\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Москва, Польша\",\"id\":\"e313a843c24f2a5643bdb828172875ea3e73556c\",\"matched_substrings\":[{\"length\":6,\"offset\":0}],\"place_id\":\"ChIJEXOLWN7NG0cRSDtN82dF1Ww\",\"reference\":\"CkQyAAAAyzjl0hEmqHLZUyXJaPZ1z6-7wklbcFg69G0eReUONjmPIlxrROsuSzRdjrfsqm7zQF80wnUum-QQ5BMXRKjcbhIQkm0l2PpigMe8_owYfTNMjhoUxkKZ4LOYJlLY6rlT8FZlnufn_Ns\",\"structured_formatting\":{\"main_text\":\"Москва\",\"main_text_matched_substrings\":[{\"length\":6,\"offset\":0}],\"secondary_text\":\"Польша\"},\"terms\":[{\"offset\":0,\"value\":\"Москва\"},{\"offset\":8,\"value\":\"Польша\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Троицк, Московская область, Россия\",\"id\":\"3bbc9d955bc707908c65a344ac6a632efe4497e6\",\"matched_substrings\":[{\"length\":6,\"offset\":0}],\"place_id\":\"ChIJS-TmTk9VNUER1byWbctR5B4\",\"reference\":\"CmRXAAAAxGk-4ov4JL1V5h5pvtaulCsEcFDwTi7rdjvUcpMYzgaaKpgVEHOVWXf3CrquIRIwA1IcVM1N3Qw1CrnOKJvyIsNxvKu2PTqD3J0ieejB8GIxmZBGJ27EHZ7jGykOknmBEhDYQzPgtrUumZstJdbkgqrsGhQoQiv_Md-Jbgc5Vd2Gb5ROdVJyCw\",\"structured_formatting\":{\"main_text\":\"Троицк\",\"main_text_matched_substrings\":[{\"length\":6,\"offset\":0}],\"secondary_text\":\"Московская область, Россия\"},\"terms\":[{\"offset\":0,\"value\":\"Троицк\"},{\"offset\":8,\"value\":\"Московская область\"},{\"offset\":28,\"value\":\"Россия\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Саларьево, Московская область, Россия\",\"id\":\"f1fe1fe30a0c0fcece5099ba58ac82bc4ddf70f1\",\"matched_substrings\":[{\"length\":9,\"offset\":0}],\"place_id\":\"ChIJR6lRu_NStUYRq4SD6aOJa_0\",\"reference\":\"CmRdAAAAUKxDovMJM1cZv7Axm6qoxXuQjRN8Thi6O3v-94QHKlA8BhN5B1hOtnyd0q0c9KJz1k_Ql-gJ5dk6eyX_sRGHjVH2Rd7K5uAj5fGxTVystSzfV2fxSLu0XUW2mXFbCEjwEhBA2AomrO3PvHHZXzFmaaE-GhTV1mkGvj6jqGx87bvFCIpj6geNWQ\",\"structured_formatting\":{\"main_text\":\"Саларьево\",\"main_text_matched_substrings\":[{\"length\":9,\"offset\":0}],\"secondary_text\":\"Московская область, Россия\"},\"terms\":[{\"offset\":0,\"value\":\"Саларьево\"},{\"offset\":11,\"value\":\"Московская область\"},{\"offset\":31,\"value\":\"Россия\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Коммунарка, Россия\",\"id\":\"8276b6462fd1f22e69b6479a806622a78c073102\",\"matched_substrings\":[{\"length\":10,\"offset\":0}],\"place_id\":\"ChIJD9s7IeisSkERVyaPm1VM310\",\"reference\":\"CkQ6AAAAc653S4WGdjkWgx8PUKyHyqz0B01Zv7eOQQ3agcF3ranWAi7FOdyegoiUTg-smtdZry_FJtMuJQyq6TW3zvb0fxIQTQ6iYzfDWywl0dUajn6tqxoUtzmY1IxIRB_4DN8uI5VdmLjo1Qc\",\"structured_formatting\":{\"main_text\":\"Коммунарка\",\"main_text_matched_substrings\":[{\"length\":10,\"offset\":0}],\"secondary_text\":\"Россия\"},\"terms\":[{\"offset\":0,\"value\":\"Коммунарка\"},{\"offset\":12,\"value\":\"Россия\"}],\"types\":[\"locality\",\"political\",\"geocode\"]}],\"status\":\"OK\"}";


    @Test
    public void getCitySuggestion() throws Exception {

        Mockito.when(placesApi.getCitySuggest(anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(new Gson().fromJson(json, CitySuggestion.class)));

        TestObserver<CitySuggestion> observable = cityRepository.getCitySuggestion("Moscow").test();
        observable.assertNoErrors();
        observable.assertValue(l ->
                Observable.fromIterable(l.getPredictions())
                        .map(Prediction::getDescription)
                        .toList()
                        .blockingGet()
                        .equals(Arrays.asList("Москва, Россия",
                                "Москва, Польша",
                                "Троицк, Московская область, Россия",
                                "Саларьево, Московская область, Россия",
                                "Коммунарка, Россия")));
    }

    @Test
    public void getCitySuggestionError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));

        Mockito.when(placesApi.getCitySuggest(anyString(), anyString(), anyString()))
                .thenReturn(Observable.error(nothing));

        TestObserver<CitySuggestion> observable = cityRepository.getCitySuggestion("Moscow").test();
        observable.assertError(nothing);
        observable.assertNotComplete();
    }


}