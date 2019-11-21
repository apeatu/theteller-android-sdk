package gh.com.payswitch.thetellerandroid.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import gh.com.payswitch.thetellerandroid.thetellerConstants;
import gh.com.payswitch.thetellerandroid.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefsRequestImpl implements DataRequest.SharedPrefsRequest {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefsRequestImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveCardDetsToSave(CardDetsToSave cardDetsToSave) {
        init();
        editor.putString("first6", cardDetsToSave.getFirst6());
        editor.putString("last4", cardDetsToSave.getLast4());
        editor.apply();
    }

    @Override
    public CardDetsToSave retrieveCardDetsToSave() {
        init();
        return new CardDetsToSave(sharedPreferences.getString("first6", ""), sharedPreferences.getString("last4", ""));
    }

    @Override
    public void saveACard(SavedCard card, String apiKey, String email) {

        List<SavedCard> savedCards = getSavedCards(email);
        List<String> checkedSavedPCards = new ArrayList<>();

        for (SavedCard s : savedCards) {
            if ((s.getFirst6() + s.getLast4())
                    .equalsIgnoreCase(card.getFirst6() + card.getLast4())){
                savedCards.remove(s);
                break;
            }
        }

        for (SavedCard savedCard: savedCards) {
            checkedSavedPCards.add(savedCard.getPan());
        }

        if (!checkedSavedPCards.contains(card.getPan())) {
            savedCards.add(card);
        }

        init();
        Gson gson = new Gson();
        Type type = new TypeToken<List<SavedCard>>() {}.getType();
        String json = gson.toJson(savedCards, type);

        Log.d("cards", json);

        editor.putString("SAVED_CARDS" +  email, json).apply();
    }

    @Override
    public List<SavedCard> getSavedCards(String email) {
        init();
        String json = sharedPreferences.getString("SAVED_CARDS" + email, "[]");

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SavedCard>>() {
            }.getType();
            return gson.fromJson(json, type);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void saveAPhone(SavedPhone phone, String apiKey, String email) {

        List<SavedPhone> savedPhones = getSavedGHMobileMoney(email);
        List<String> checkedSavedPhones = new ArrayList<>();

        for (SavedPhone savedPhone: savedPhones) {
            checkedSavedPhones.add(savedPhone.getPhoneNumber());
        }
        Log.wtf("checked list", checkedSavedPhones.toString());

        if (!checkedSavedPhones.contains(phone.getPhoneNumber())) {
            savedPhones.add(phone);
        }
        Log.wtf("contains boolean", !checkedSavedPhones.contains(phone.getPhoneNumber())+"");

        init();
        Gson gson = new Gson();
        Type type = new TypeToken<List<SavedPhone>>() {}.getType();
        String json = gson.toJson(savedPhones, type);

        editor.putString("SAVED_PHONE_NUMBER" +  email, json).apply();
        Log.d("phones", json);
    }

    @Override
    public List<SavedPhone> getSavedGHMobileMoney(String email) {
        init();
        String json = sharedPreferences.getString("SAVED_PHONE_NUMBER" + email, "[]");

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SavedPhone>>() {
            }.getType();
            return gson.fromJson(json, type);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void init() {

        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(
                    thetellerConstants.theteller, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }
}
