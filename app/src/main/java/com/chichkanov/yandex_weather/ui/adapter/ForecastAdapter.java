package com.chichkanov.yandex_weather.ui.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.utils.WeatherUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_WEATHER = 0;
    private final int TYPE_FORECAST = 1;

    private List<Forecast> forecasts;
    private CurrentWeather currentWeather;
    private Context context;


    public ForecastAdapter(@NonNull Context context, @NonNull List<Forecast> forecasts) {
        this.forecasts = forecasts;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ForecastViewHolder) {
            int pos = position - 1;
            ForecastViewHolder viewHolder = (ForecastViewHolder) holder;
            viewHolder.showForecast(context, forecasts.get(pos));
        }

        if (holder instanceof WeatherViewHolder) {
            WeatherViewHolder viewHolder = (WeatherViewHolder) holder;
            viewHolder.showWeather(context, currentWeather);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FORECAST) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.forecast_item, parent, false);

            ForecastViewHolder vh = new ForecastViewHolder(v);
            return vh;
        }
        if (viewType == TYPE_WEATHER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.current_weather_header, parent, false);

            WeatherViewHolder vh = new WeatherViewHolder(v);
            return vh;
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return forecasts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_WEATHER;
        }
        return TYPE_FORECAST;
    }

    public void setForecasts(List<Forecast> forecasts) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new ForecastItemDiffCallback(this.forecasts, forecasts), false);
        this.forecasts = forecasts;
        result.dispatchUpdatesTo(ForecastAdapter.this);
    }

    public void setWeather(CurrentWeather weather) {
        currentWeather = weather;
        notifyItemChanged(0);
    }


    static class ForecastViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.date_text_view)
        TextView dateTextView;
        @BindView(R.id.weather_image_view)
        ImageView weatherImageView;
        @BindView(R.id.min_temp_text_view)
        TextView minTempTextView;
        @BindView(R.id.max_temp_text_view)
        TextView maxTempTextView;
        @BindView(R.id.morning_temp_text_view)
        TextView morningTempTextView;
        @BindView(R.id.day_temp_text_view)
        TextView dayTempTextView;
        @BindView(R.id.evening_temp_text_view)
        TextView eveningTempTextView;
        @BindView(R.id.night_temp_text_view)
        TextView nightTempTextView;
        @BindView(R.id.linear_layout)
        LinearLayout linearLayout;
        private int originalHeight = 0;
        private boolean isViewExpanded = false;

        ForecastViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);

            if (!isViewExpanded) {
                linearLayout.setVisibility(View.GONE);
                linearLayout.setEnabled(false);
            }
        }

        void showForecast(Context context, Forecast forecast) {
            Date now = new Date(forecast.getDateTime() * 1000);

            String datePattern = "EE, dd MMMM";
            SimpleDateFormat formatter = new SimpleDateFormat(datePattern, Locale.getDefault());
            String formattedDate = formatter.format(now);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
                dateTextView.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                dateTextView.setTextColor(context.getResources().getColor(android.R.color.black));
            }

            dateTextView.setText(formattedDate);
            minTempTextView.setText(WeatherUtils.formatTemperature(forecast.getMinTemp()));
            maxTempTextView.setText(WeatherUtils.formatTemperature(forecast.getMaxTemp()));
            weatherImageView.setImageDrawable(
                    context.getResources().getDrawable(
                            WeatherUtils.getImageIdByName(forecast.getIcon())));

            morningTempTextView.setText(WeatherUtils.formatTemperature(forecast.getMorningTemp()));
            dayTempTextView.setText(WeatherUtils.formatTemperature(forecast.getDayTemp()));
            eveningTempTextView.setText(WeatherUtils.formatTemperature(forecast.getEveningTemp()));
            nightTempTextView.setText(WeatherUtils.formatTemperature(forecast.getNightTemp()));
        }

        @Override
        public void onClick(final View view) {
            if (originalHeight == 0) {
                originalHeight = view.getHeight();
            }

            ValueAnimator valueAnimator;
            if (!isViewExpanded) {
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout.setEnabled(true);
                isViewExpanded = true;
                valueAnimator = ValueAnimator.ofInt(originalHeight, originalHeight + (int) (originalHeight * 1.25));
            } else {
                isViewExpanded = false;
                valueAnimator = ValueAnimator.ofInt(originalHeight + (int) (originalHeight * 1.5), originalHeight);

                Animation animation = new AlphaAnimation(1.00f, 0.00f);

                animation.setDuration(200);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        linearLayout.setVisibility(View.INVISIBLE);
                        linearLayout.setEnabled(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                linearLayout.startAnimation(animation);
            }
            valueAnimator.setDuration(200);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(animation -> {
                Integer value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = value.intValue();
                view.requestLayout();
            });
            valueAnimator.start();
        }
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.weather_image_view)
        ImageView weatherImageView;
        @BindView(R.id.temp_text_view)
        TextView currentTempTextView;
        @BindView(R.id.min_temp_text_view)
        TextView minTempTextView;
        @BindView(R.id.max_temp_text_view)
        TextView maxTempTextView;
        @BindView(R.id.description_text_view)
        TextView descriptionTextView;
        @BindView(R.id.humidity_text_view)
        TextView humidityTextView;
        @BindView(R.id.pressure_text_view)
        TextView pressureTextView;
        @BindView(R.id.wind_text_view)
        TextView windTextView;
        @BindView(R.id.sunrise_text_view)
        TextView sunriseTextView;
        @BindView(R.id.sunset_text_view)
        TextView sunsetTextView;

        WeatherViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void showWeather(@NonNull Context context, @Nullable CurrentWeather currentWeather) {
            if (currentWeather != null) {
                currentTempTextView.setText(WeatherUtils.formatTemperature(currentWeather.getTemp()));
                minTempTextView.setText(WeatherUtils.formatTemperature(currentWeather.getMinTemp()));
                maxTempTextView.setText(WeatherUtils.formatTemperature(currentWeather.getMaxTemp()));
                descriptionTextView.setText(currentWeather.getDescription());
                weatherImageView.setImageDrawable(
                        context.getResources().getDrawable(
                                WeatherUtils.getImageIdByName(currentWeather.getIcon())));
                pressureTextView.setText(String.format(context.getResources().getString(R.string.pressure),
                        String.valueOf(currentWeather.getPressure())));
                humidityTextView.setText(String.format(context.getResources().getString(R.string.humidity),
                        String.valueOf(currentWeather.getHumidity())));
                windTextView.setText(String.format(context.getResources().getString(R.string.wind),
                        String.valueOf(currentWeather.getWindSpeed())));

                String sunsetPattern = "HH:mm";
                SimpleDateFormat sunsetFormatter = new SimpleDateFormat(sunsetPattern, Locale.getDefault());

                Date sunrise = new Date(currentWeather.getSunrise() * 1000);
                String formattedDate = sunsetFormatter.format(sunrise);
                sunriseTextView.setText(formattedDate);

                Date sunset = new Date(currentWeather.getSunset() * 1000);
                formattedDate = sunsetFormatter.format(sunset);
                sunsetTextView.setText(formattedDate);
            }
        }
    }
}