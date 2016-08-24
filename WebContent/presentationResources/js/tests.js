QUnit.test( "hello test", function( assert ) {
  assert.ok( 1 == "1", "Passed!" );
});


QUnit.test("Search Docs page init test", function( assert ) {
	assert.ok(searchDocsPageInit(), "Passed!" );
});

QUnit.test("Search Page init test", function( assert ) {
	assert.ok(searchPageReadyInit(), "Passed!" );
});