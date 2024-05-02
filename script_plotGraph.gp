set terminal png size 500,500
set output 'executionTimes.png'
set title 'Execution Time'
set xlabel 'Number of lines'
set ylabel 'Time (ms)'
set xrange [0:*]
set yrange [0:100000]  # Adjusted yrange to automatically fit the data
set datafile separator ';'  # Corrected separator to comma
plot 'execution_times.csv' using 1:2 with linespoints  # Plotting with linespoints
