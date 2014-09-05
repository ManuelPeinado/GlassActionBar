GlassActionBar
==================

GlassActionBar is an Android library which implements a glass-like effect for the action bar. 

The three most commonly used action bar implementations are supported: stock (API >13), ActionBarCompat and ActionBarSherlock. 

The code of this library is based on techniques outlined by Nicolas Pomepuy in [a recent blog post][1].

![Example Image][2]

Try out the sample application:

<a href="https://play.google.com/store/apps/details?id=com.manuelpeinado.glassactionbardemo">
  <img alt="Android app on Google Play"
       src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

Or browse the [source code of the sample application][3] for a complete example of use.

Including in your project
-------------------------

The library is pushed to Maven Central as a AAR, so you just need to add the following dependency to your `build.gradle`.
    
    dependencies {
        compile 'com.github.manuelpeinado.glassactionbar:glassactionbar:0.3.0'
    }
    
Or if your project doesn't use the stock action bar, but one of the compatibility implementations, you would use the following:

    dependencies {
        // Use the following if your project uses ActionBarCompat
        compile 'com.github.manuelpeinado.glassactionbar:glassactionbar-abc:0.3.0'
        // Or the following if your project uses ActionBarSherlock
        compile 'com.github.manuelpeinado.glassactionbar:glassactionbar-abs:0.3.0'
    }

Usage
-----

Using the library is really simple, just look at the source code of the provided samples:
* [Stock action bar][3].
* [ActionBarCompat][4]
* [ActionBarSherlock][5]


Acknowledgements
--------------------

* Thanks to [Nicolas Pomepuy][1] for sharing the techniques that make this library possible.
* The gaussian blur effect is based on code by [Mario Klingemann][8] which was ported to Android by [Yahel Bouaziz][9].
* NotifyingScrollView class by [Cyril Mottier][10].
* The project organization is heavily inspired by Chris Bane's [ActionBar-PullToRefresh][11] library.
* Cat icon by [Davic Vignoni][12].

Who's using it
--------------

*Does your app use GlassActionBar? If you want to be featured on this list drop me a line.*


Developed By
--------------------

Manuel Peinado Gallego - <manuel.peinado@gmail.com>

<a href="https://twitter.com/mpg2">
  <img alt="Follow me on Twitter"
       src="https://raw.github.com/ManuelPeinado/NumericPageIndicator/master/art/twitter.png" />
</a>
<a href="https://plus.google.com/106514622630861903655">
  <img alt="Follow me on Google+"
       src="https://raw.github.com/ManuelPeinado/NumericPageIndicator/master/art/google-plus.png" />
</a>
<a href="http://www.linkedin.com/pub/manuel-peinado-gallego/1b/435/685">
  <img alt="Follow me on LinkedIn"
       src="https://raw.github.com/ManuelPeinado/NumericPageIndicator/master/art/linkedin.png" />


License
-----------

    Copyright 2013 Manuel Peinado

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




 [1]: http://nicolaspomepuy.fr/?p=18
 [2]: https://raw.github.com/ManuelPeinado/GlassActionBar/master/art/readme_pic.png
 [3]: https://github.com/ManuelPeinado/GlassActionBar/tree/master/samples-stock 
 [4]: https://github.com/ManuelPeinado/GlassActionBar/tree/master/samples-actionbarcompat
 [5]: https://github.com/ManuelPeinado/GlassActionBar/tree/master/extras-actionbarsherlock
 [6]: https://github.com/mosabua/maven-android-sdk-deployer
 [7]: https://github.com/ManuelPeinado/GlassActionBar/tree/master/samples
 [8]: http://www.quasimondo.com/
 [9]: https://plus.google.com/107352914145283602089
 [10]: http://www.cyrilmottier.com
 [11]: https://github.com/chrisbanes/ActionBar-PullToRefresh  
 [12]: http://www.icon-king.com/

