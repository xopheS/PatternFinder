

<img src="https://user-images.githubusercontent.com/36798245/48853523-fd53fe00-edaf-11e8-8799-9522c0910168.jpg" alt="drawing" width="200" align="middle"/>

<h1 id="description">Description</h1>
<p>(Full description in pdf file) PatternFinder is a Java application which matches an image in a background. It can use two techniques for pattern matching :</p>
<ol>
<li><p>Distance Based Search</p></li>
<li><p>Similarity Based Search</p></li>
</ol>
<p>Both techniques operates the same way: we slide the pattern along the background, calculate some coefficient which will help us decide of the position of the pattern.</p>
<h1 id="content">Content</h1>
<ol>
<li><p>PatternFinder project folder</p></li>
<li><p>PatternFinder runnable jar file</p></li>
<li><p>README.pdf</p></li>
</ol>
<h1 id="structure">Structure</h1>
<h2 id="image-processing">Image Processing</h2>
<p>We begin by developping tools for processing images. Transforming an image into a table of RGB (int[][]) and then be able to extract each component of this RGB color (done by manipulating bits, shifting, and, or...). We also need to transform each RGB color into a shade of gray and vice versa.</p>
<h2 id="distance-based-search">Distance Based Search</h2>
<p>For each possible position of the pattern image in the background image, we calculate the distance between pixel values and put them in a matrix, the distance matrix. We use the absolute mean error to measure the distance between two pixels. And then we calculate the distance between the pattern image and the sub-background image.</p>
<h2 id="similarity-based-search">Similarity Based Search</h2>
<p>Same as 3.2 but we use a coefficient of correlation instead to fill a similarity matrix.</p>
<h2 id="finding-best-fit">Finding best fit</h2>
<p>Two methods to find the n best values:</p>
<ol>
<li><p>Brute force : walk along the matrix and extract best value n times <span class="math inline"> → <em>O</em>(<em>n</em> × <em>p</em>)</span></p></li>
<li><p>Quicksort : sort matrix elements using quicksort and then get the best values <span class="math inline"> → <em>O</em>(<em>p</em><em>l</em><em>o</em><em>g</em>(<em>p</em>))</span></p></li>
</ol>
<p>where <span class="math inline"><em>p</em></span> is the number of pixels of <span class="math inline"><em>B</em></span></p>
