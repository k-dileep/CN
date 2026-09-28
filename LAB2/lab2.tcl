#lab2.tcl

# Create Simulator
set ns [new Simulator]

# Open NAM and Trace files
set nf [open out2.nam w]
set tf [open out2.tr w]

$ns namtrace-all $nf
$ns trace-all $tf

# Create 6 nodes
for {set i 0} {$i < 6} {incr i} {
    set n$i [$ns node]
}

# Change n4 shape
$n4 shape box

# Create network topology
$ns duplex-link $n0 $n4 1005Mb 1ms DropTail
$ns duplex-link $n1 $n4 50Mb 1ms DropTail
$ns duplex-link $n2 $n4 2000Mb 1ms DropTail
$ns duplex-link $n3 $n4 200Mb 1ms DropTail
$ns duplex-link $n4 $n5 1Mb 1ms DropTail

# Create Ping agent at n0
set p1 [new Agent/Ping]
$ns attach-agent $n0 $p1
$p1 set packetSize_ 50000
$p1 set interval_ .0001

# Create Ping agent at n1
set p2 [new Agent/Ping]
$ns attach-agent $n1 $p2

# Create Ping agent at n2
set p3 [new Agent/Ping]
$ns attach-agent $n2 $p3
$p3 set packetSize_ 30000
$p3 set interval_ .00001

# Create Ping agent at n3
set p4 [new Agent/Ping]
$ns attach-agent $n3 $p4

# Create Ping agent at n5
set p5 [new Agent/Ping]
$ns attach-agent $n5 $p5

# Set queue limits
$ns queue-limit $n0 $n4 5
$ns queue-limit $n2 $n4 3
$ns queue-limit $n4 $n5 2

# Display received Ping messages
Agent/Ping instproc recv {from rtt} {
    $self instvar node_
    puts "node [$node_ id] received answer from $from with round trip time $rtt ms"
}

# Connect Ping agents
$ns connect $p1 $p5
$ns connect $p3 $p4

# Finish procedure
proc finish {} {
    global ns nf tf
    $ns flush-trace
    close $nf
    close $tf

    exec nam out/out2.nam &
    exit 0
}

# Send Ping messages
foreach i [list $p1 $p3] {
    for {set j 1} {$j < 30} {incr j} {
        $ns at [format "%1.1f" [expr {$j * .1}]] "$i send"
    }
}

# Stop simulation
$ns at 3.0 "finish"

# Run simulation
$ns run


#ns lab2.tcl

#grep "^d" out2.tr | wc -l