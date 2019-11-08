<h1>Circular Pulsing Button (This library is no longer maintained)</h1>
<p>A simple circular button for Android that has a pulsing effect when you click on it.</p>
<h2>How to Add the Library</h2>
<p>This library is availabe in the jcenter repository. Simply add this line of code in your dependencies:</p>

```
compile 'ir.sohreco.circularpulsingbutton:circular-pulsing-button:1.1.1'
```

<h2>How to Use</h2>
<p>You can see an example of adding this button in your layouts in the code below:</p>

```xml
<ir.sohreco.circularpulsingbutton.CircularPulsingButton
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_centerInParent="true"
        app:cpb_text="Hello World!"
        app:cpb_zoomOutScale="0.5"
        app:cpb_zoomInScale="1.5"
        app:cpb_animationDuration="300"/>
```

<b>Note that zoomOutScale must be a float value between 0 and 1.</b><br>
<b>zoomInScale must be a float value between 1 and 2. Button size gets smaller when you increase zoomInScale.</b>
