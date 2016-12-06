<h1>Circular Pulsing Button</h1>
<p>A simple circular button for Android that has a pulsing effect when you click on it.</p>
<h2>How to Add the Library</h2>
<p>This library is availabe in the jcenter repository. Simply add this line of code in your dependencies:</p>
```
compile 'ir.sohreco.circularpulsingbutton:circular-pulsing-button:1.0.0'
```
<h2>How to Use</h2>
<p>You can see an example of adding this button in your layouts in the code below:</p>
```xml
<ir.sohreco.circularpulsingbutton.CircularPulsingButton
        android:id="@+id/cpb"
        android:layout_width="100dp"
        android:layout_height="100dp"
        app:cpb_text="Click Me!"
        app:cpb_color="@color/colorAccent"
        app:cpb_textColor="#FFFF"
        app:cpb_zoomOutScale="0.8"/>
```
<b>Note that zoomOutScale must be a float value between 0 and 1.</b>
