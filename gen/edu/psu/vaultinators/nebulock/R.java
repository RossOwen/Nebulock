/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package edu.psu.vaultinators.nebulock;

public final class R {
    public static final class attr {
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarButtonStyle=0x7f010001;
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarStyle=0x7f010000;
    }
    public static final class color {
        public static final int black_overlay=0x7f040000;
    }
    public static final class dimen {
        public static final int activity_horizontal_margin=0x7f050001;
        public static final int activity_vertical_margin=0x7f050000;
    }
    public static final class drawable {
        public static final int ic_launcher=0x7f020000;
    }
    public static final class id {
        public static final int buttonCreateAccount=0x7f08000b;
        public static final int buttonCreateKeyring=0x7f080015;
        public static final int buttonKeyringExplanation=0x7f080017;
        public static final int editTextCreateAccountConfirmEmail=0x7f080005;
        public static final int editTextCreateAccountConfirmPassword=0x7f080009;
        public static final int editTextCreateAccountEmail=0x7f080003;
        public static final int editTextCreateAccountPassword=0x7f080007;
        public static final int editTextCreateKeyringConfirmMasterPassword=0x7f08000f;
        public static final int editTextCreateKeyringMasterPassword=0x7f08000e;
        public static final int editTextCreateKeyringSecurityAnswer=0x7f080014;
        public static final int editTextCreateKeyringSecurityQuestion=0x7f080012;
        public static final int editTextEmail=0x7f08001a;
        public static final int editTextPassword=0x7f08001c;
        public static final int loginButton=0x7f08001d;
        public static final int textViewAppName=0x7f080018;
        public static final int textViewCreateAccount=0x7f080000;
        public static final int textViewCreateAccountConfirmEmail=0x7f080004;
        public static final int textViewCreateAccountConfirmPassword=0x7f080008;
        public static final int textViewCreateAccountEmail=0x7f080002;
        public static final int textViewCreateAccountPassword=0x7f080006;
        public static final int textViewCreateKeyring=0x7f08000c;
        public static final int textViewCreateKeyringConfirmMasterPassword=0x7f080010;
        public static final int textViewCreateKeyringMasterPassword=0x7f08000d;
        public static final int textViewCreateKeyringSecurityAnswer=0x7f080013;
        public static final int textViewCreateKeyringSecurityQuestion=0x7f080011;
        public static final int textViewEmail=0x7f080019;
        public static final int textViewForgotPassword=0x7f08001e;
        public static final int textViewKeyringExplanation=0x7f080001;
        public static final int textViewPassword=0x7f08001b;
        public static final int textViewStrength=0x7f08000a;
        public static final int textViewWhatIsAKeyring=0x7f080016;
    }
    public static final class layout {
        public static final int activity_create_account=0x7f030000;
        public static final int activity_create_keyring=0x7f030001;
        public static final int activity_keyring_information=0x7f030002;
        public static final int activity_login_screen=0x7f030003;
        public static final int activity_vault_home=0x7f030004;
    }
    public static final class string {
        public static final int LoginScreen=0x7f060007;
        public static final int app_name=0x7f060000;
        public static final int confirmemailaddress=0x7f060004;
        public static final int confirmmasterpassword=0x7f060013;
        public static final int confirmpassword=0x7f060006;
        public static final int createaccount=0x7f060001;
        public static final int createkeyring=0x7f060011;
        public static final int done=0x7f06000c;
        public static final int dont_have_account=0x7f06000a;
        public static final int email=0x7f060002;
        public static final int emailaddress=0x7f060003;
        public static final int entermasterpassword=0x7f060012;
        public static final int forgot_password=0x7f060009;
        public static final int keyringexplanation=0x7f06000f;
        public static final int keyringexplanationbutton=0x7f060010;
        public static final int next=0x7f06000b;
        public static final int password=0x7f060005;
        public static final int securityquestion=0x7f060014;
        public static final int securityquestionanswer=0x7f060015;
        public static final int settings=0x7f060008;
        public static final int strength=0x7f06000d;
        public static final int whatsakeyring=0x7f06000e;
    }
    public static final class style {
        /** 
        Base application theme, dependent on API level. This theme is replaced
        by AppBaseTheme from res/values-vXX/styles.xml on newer devices.
    

            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.
        

        Base application theme for API 11+. This theme completely replaces
        AppBaseTheme from res/values/styles.xml on API 11+ devices.
    
 API 11 theme customizations can go here. 

        Base application theme for API 14+. This theme completely replaces
        AppBaseTheme from BOTH res/values/styles.xml and
        res/values-v11/styles.xml on API 14+ devices.
    
 API 14 theme customizations can go here. 
         */
        public static final int AppBaseTheme=0x7f070000;
        /**  Application theme. 
 All customizations that are NOT specific to a particular API-level can go here. 
         */
        public static final int AppTheme=0x7f070001;
        public static final int ButtonBar=0x7f070003;
        public static final int ButtonBarButton=0x7f070004;
        public static final int FullscreenActionBarStyle=0x7f070005;
        public static final int FullscreenTheme=0x7f070002;
    }
    public static final class styleable {
        /** 
         Declare custom theme attributes that allow changing which styles are
         used for button bars depending on the API level.
         ?android:attr/buttonBarStyle is new as of API 11 so this is
         necessary to support previous API levels.
    
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarButtonStyle edu.psu.vaultinators.nebulock:buttonBarButtonStyle}</code></td><td></td></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarStyle edu.psu.vaultinators.nebulock:buttonBarStyle}</code></td><td></td></tr>
           </table>
           @see #ButtonBarContainerTheme_buttonBarButtonStyle
           @see #ButtonBarContainerTheme_buttonBarStyle
         */
        public static final int[] ButtonBarContainerTheme = {
            0x7f010000, 0x7f010001
        };
        /**
          <p>This symbol is the offset where the {@link edu.psu.vaultinators.nebulock.R.attr#buttonBarButtonStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name edu.psu.vaultinators.nebulock:buttonBarButtonStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarButtonStyle = 1;
        /**
          <p>This symbol is the offset where the {@link edu.psu.vaultinators.nebulock.R.attr#buttonBarStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name edu.psu.vaultinators.nebulock:buttonBarStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarStyle = 0;
    };
}
