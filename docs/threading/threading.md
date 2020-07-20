Webicity uses Ribbon, which currently does all drawing on a single UI thread.

For each webicity task, two additional threads exist:
* A rendering thread
* An IO thread

The rendering thread executes the generate tasks that are required to render and execute a web page, such as:
* Running scripts
* Parsing content
* Generating a layout

The IO thread manages heavy IO operations that may take a while and don't really have an effect on the web page until they have completed.

The IO thread reads 8kib of data at a time, and sends it to the rendering thread. Rendering happens each time the the browser is waiting on the IO thread.