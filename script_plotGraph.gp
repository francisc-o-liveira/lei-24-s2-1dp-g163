set terminal png size 500,500
set output 'executionTimes.png'
set title 'Execution Time'
set xlabel 'Number of lines'
set ylabel 'Time (ms)'
set xrange [0:*]
set yrange [0:*]
set datafile separator ';'
plot 'execution_times.csv' using 1:2 with linespoints
