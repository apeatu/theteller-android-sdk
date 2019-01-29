
package gh.com.payswitch.thetellerandroid;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated("org.parceler.ParcelAnnotationProcessor")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class thetellerInitializer$$Parcelable
    implements Parcelable, ParcelWrapper<gh.com.payswitch.thetellerandroid.thetellerInitializer>
{

    private gh.com.payswitch.thetellerandroid.thetellerInitializer thetellerInitializer$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<thetellerInitializer$$Parcelable>CREATOR = new Creator<thetellerInitializer$$Parcelable>() {


        @Override
        public thetellerInitializer$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new thetellerInitializer$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public thetellerInitializer$$Parcelable[] newArray(int size) {
            return new thetellerInitializer$$Parcelable[size] ;
        }

    }
    ;

    public thetellerInitializer$$Parcelable(gh.com.payswitch.thetellerandroid.thetellerInitializer thetellerInitializer$$2) {
        thetellerInitializer$$0 = thetellerInitializer$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(thetellerInitializer$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(gh.com.payswitch.thetellerandroid.thetellerInitializer thetellerInitializer$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(thetellerInitializer$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(thetellerInitializer$$1));
            parcel$$1 .writeString(thetellerInitializer$$1 .lName);
            parcel$$1 .writeDouble(thetellerInitializer$$1 .amount);
            parcel$$1 .writeString(thetellerInitializer$$1 .apiKey);
            parcel$$1 .writeString(thetellerInitializer$$1 .voucher_code);
            parcel$$1 .writeString(thetellerInitializer$$1 .merchant_id);
            parcel$$1 .writeString(thetellerInitializer$$1 .apiUser);
            parcel$$1 .writeInt((thetellerInitializer$$1 .withGHMobileMoney? 1 : 0));
            parcel$$1 .writeString(thetellerInitializer$$1 .fName);
            parcel$$1 .writeInt((thetellerInitializer$$1 .withCard? 1 : 0));
            parcel$$1 .writeString(thetellerInitializer$$1 .txRef);
            parcel$$1 .writeInt((thetellerInitializer$$1 .allowSaveCard? 1 : 0));
            parcel$$1 .writeString(thetellerInitializer$$1 .meta);
            parcel$$1 .writeString(thetellerInitializer$$1 .narration);
            parcel$$1 .writeString(thetellerInitializer$$1 .currency);
            parcel$$1 .writeInt(thetellerInitializer$$1 .theme);
            parcel$$1 .writeString(thetellerInitializer$$1 .d_response_url);
            parcel$$1 .writeString(thetellerInitializer$$1 .payment_plan);
            parcel$$1 .writeInt((thetellerInitializer$$1 .staging? 1 : 0));
            parcel$$1 .writeString(thetellerInitializer$$1 .email);
            parcel$$1 .writeString(thetellerInitializer$$1 .terminal_id);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public gh.com.payswitch.thetellerandroid.thetellerInitializer getParcel() {
        return thetellerInitializer$$0;
    }

    public static gh.com.payswitch.thetellerandroid.thetellerInitializer read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            gh.com.payswitch.thetellerandroid.thetellerInitializer thetellerInitializer$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            thetellerInitializer$$4 = new gh.com.payswitch.thetellerandroid.thetellerInitializer();
            identityMap$$1 .put(reservation$$0, thetellerInitializer$$4);
            thetellerInitializer$$4 .lName = parcel$$3 .readString();
            thetellerInitializer$$4 .amount = parcel$$3 .readDouble();
            thetellerInitializer$$4 .apiKey = parcel$$3 .readString();
            thetellerInitializer$$4 .voucher_code = parcel$$3 .readString();
            thetellerInitializer$$4 .merchant_id = parcel$$3 .readString();
            thetellerInitializer$$4 .apiUser = parcel$$3 .readString();
            thetellerInitializer$$4 .withGHMobileMoney = (parcel$$3 .readInt() == 1);
            thetellerInitializer$$4 .fName = parcel$$3 .readString();
            thetellerInitializer$$4 .withCard = (parcel$$3 .readInt() == 1);
            thetellerInitializer$$4 .txRef = parcel$$3 .readString();
            thetellerInitializer$$4 .allowSaveCard = (parcel$$3 .readInt() == 1);
            thetellerInitializer$$4 .meta = parcel$$3 .readString();
            thetellerInitializer$$4 .narration = parcel$$3 .readString();
            thetellerInitializer$$4 .currency = parcel$$3 .readString();
            thetellerInitializer$$4 .theme = parcel$$3 .readInt();
            thetellerInitializer$$4 .d_response_url = parcel$$3 .readString();
            thetellerInitializer$$4 .payment_plan = parcel$$3 .readString();
            thetellerInitializer$$4 .staging = (parcel$$3 .readInt() == 1);
            thetellerInitializer$$4 .email = parcel$$3 .readString();
            thetellerInitializer$$4 .terminal_id = parcel$$3 .readString();
            gh.com.payswitch.thetellerandroid.thetellerInitializer thetellerInitializer$$3 = thetellerInitializer$$4;
            identityMap$$1 .put(identity$$1, thetellerInitializer$$3);
            return thetellerInitializer$$3;
        }
    }

}
