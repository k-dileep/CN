#lab1.tcl

# Create Simulator
set ns [new Simulator]

# Open Trace file and NAM file in current directory
set ntrace [open out1.tr w]
set namfile [open out1.nam w]

$ns trace-all $ntrace
$ns namtrace-all $namfile

# Finish Procedure
proc finish {} {
   global ns ntrace namfile

   # Flush and close trace files
   $ns flush-trace
   close $ntrace
   close $namfile

   # Count packet drops
   set drops [exec grep -c "^d" out1.tr]
   puts "The number of packet drops is $drops"

   # Open NAM
   exec nam out1.nam &
   exit 0
}

# Create 3 nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

# Label nodes
$n0 label "Source"
$n1 label "Router"
$n2 label "Sink"

# Set color
$ns color 1 blue

# Create duplex point-to-point links
# Change bandwidth here for different experiments
$ns duplex-link $n0 $n1 2Mb 10ms DropTail
$ns duplex-link $n1 $n2 512kb 10ms DropTail

# Set link orientation
$ns duplex-link-op $n0 $n1 orient right
$ns duplex-link-op $n1 $n2 orient right

# Set queue size
$ns queue-limit $n0 $n1 10
$ns queue-limit $n1 $n2 10

# Create UDP agent
set udp0 [new Agent/UDP]
$ns attach-agent $n0 $udp0

# Create CBR traffic
set cbr0 [new Application/Traffic/CBR]

$cbr0 set packetSize_ 500
$cbr0 set rate_ 2Mb
$cbr0 set random_ false

$cbr0 attach-agent $udp0

# Create Null sink
set sink [new Agent/Null]
$ns attach-agent $n2 $sink

# Connect UDP source to sink
$ns connect $udp0 $sink

# Set packet color
$udp0 set class_ 1

# Schedule events
$ns at 0.0 "$cbr0 start"
$ns at 5.0 "$cbr0 stop"
$ns at 5.0 "finish"

# Run simulation
$ns run


#ns lab1.tcl