
my @array;

# ['1', '2']
@array = (
	$map{"datapowers"}[0],
	$map{"datapowers"}[1]
);
print Dumper(\@array);

# [['1', '2]]
@array = @map{"datapowers"};
print Dumper(\@array);

# [['1', '2]]
@array = $map{"datapowers"};
print Dumper(\@array);

# ['1', '2']
print Dumper($map{"datapowers"});


# ['1', '2']
foreach my $value (@{ $map{"datapowers"} }) {
	print $value . "\n";
}

# Global symbol requires explicit package name
#foreach my $value (@$map{"datapowers"}) {
#	print $value . "\n";
#}

# ARRAY(0x2d86378)
foreach my $value ($map{"datapowers"}) {
	print $value . "\n";
}

# http://stackoverflow.com/a/12165299/1655155
# Global symbol requires explicit package name
#@array = $map -> {"datapowers"} # dereferencing with the -> operator
#@array = $$map{"datapowers"} # same as above with syntactic sugar