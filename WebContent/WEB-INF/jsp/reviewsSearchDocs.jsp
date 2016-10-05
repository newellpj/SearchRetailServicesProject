

@media screen and (max-width:1600px){
	button.accordion.responsive { width:50%;}
	div.search-box.responsive {position: relative; padding:1em; margin-left:25%; width:60%;}
		 div#resultsSection.resultsSection.responsive{position: relative; padding:1em; margin-left:25%; width:60%;}
}


@media screen and (max-width:1300px){
	div.search-box.responsive {position: relative; padding:1em; margin-left:10em; width:70%;}
	  button.accordion.responsive {position: relative; margin-left:10em; padding:1em; width:68%;}
	 div.frameContainer.responsive{position: relative; padding:0; margin:0; width:100%;}
	 div#resultsSection.resultsSection.responsive{position: relative; padding:1em; margin-left:10em; width:70%;}
}

@media screen and (max-width:1080px){
 div#search-box.search-box.responsive {position: relative; padding:1em; margin:1em; min-width:90%;}
  div.frameContainer.responsive{position: relative; padding:0; margin:0; width:90%;}
  button.accordion.responsive {position: relative; margin:0.5em; padding:2em; width:90%;}
   div#resultsSectionDocs.resultsSectionDocs.responsive{position: relative; padding:1em; margin:1em; min-width:90%;}
   	 button#searchBook{min-width:45%; margin:0.5em;}
	button#resetSearch{min-width:45%;margin:0.5em;}
	div.tagSearches{margin:0;}
}


@media screen and (max-width:720px){
 div.search-box.responsive {position: relative; padding:0.4em; margin:0.5em; min-width:95%; display:block;}
 div#resultsSection.resultsSection.responsive {position: relative; padding:0.4em; margin:0.5em; min-width:95%; display:block;}

	button.accordion.responsive {position: relative; margin:0.5em;  padding:2em; width:98%;}
	 div.tagSearches.responsive{margin:0;min-width:98%;display:inline-block;}
	 .searchBook.responsive{min-width:40%; margin:1.5em;}
	.resetSearch.responsive{min-width:40%;margin:1.5em;}
	
	input#titleText{min-width:83%; margin-bottom:1em;}

	 
	 input#authorText{min-width:83%; margin-bottom:1em;}
	
	 
	 div.authorCategory.responsive{margin-left:1em;}
	 div.publisherLang.responsive{margin-left:1em;}
	 div.tagSearches.responsive{margin:0;}
}



@media screen and (max-width:600px){
	 div.titleAndGenre.responsive{ display:block; margin:0.2em}
	 div.authorCategory.responsive{ display:block; margin:0.2em}
	 div.publisherLang.responsive{ display:block; margin:0.2em}
	 
	 input#titleText{min-width:85%; margin-bottom:1em;}
	 select#genreSelect{min-width:70%; margin-bottom:1em;}
	 
	 input#authorText{min-width:80%; margin-bottom:1em;}
	 button#searchBook.searchBook{min-width:95%; margin:0.5em; display:block;}
	button#resetSearch.resetSearch{min-width:95%;margin:0.5em;display:block;}
	div.tagSearches.responsive{display:block; min-width:99%; margin:0;}

}

@media screen and (max-width:540px){
	input#titleText{min-width:80%; margin-bottom:1em;}

	

	 input#authorText{min-width:77%; margin-bottom:1em;}

	 div.tagSearches.responsive{margin:0;min-width:98%;display:block;}
	 .searchBook.responsive{min-width:90%; margin:0.5em;}
	.resetSearch.responsive{min-width:90%;margin:0.5em;}

}

@media screen and (max-width:470px){
		input#publisherText{min-width:70%; margin-bottom:1em;}
}

@media screen and (max-width:420px){
	input#titleText{min-width:75%; margin-bottom:1em;}


	input#authorText{min-width:71%; margin-bottom:1em;}



	
}

@media screen and (max-width:370px){
	input#authorText{min-width:69%; margin-bottom:1em;}
	input#publisherText{min-width:64%; margin-bottom:1em;}
}

@media screen and (max-width:350px){
	input#titleText{min-width:73%; margin-bottom:1em; border-radius:7px 7px 7px 7px; border-right: solid 1.5px #dcdcdc;}
	input#authorText{min-width:68%; margin-bottom:1em; border-radius:7px 7px 7px 7px; border-right: solid 1.5px #dcdcdc;}

      .glyphicon-pencil{visibility:hidden;}
	  .glyphicon-book{visibility:hidden;}

}

.docsSearchSegment{
	border-top: 1px dotted #e9e9e9;
	border-bottom: 1px dotted #e9e9e9;
	border-right: 0px dotted;
	border-left: 0px dotted;
	min-height:3%;
	max-height:3%;
	margin:1em;
	padding:5px ;
	overflow:hidden ;
	display: block;
	min-width: 90%;
	text-overflow: ellipsis !important;
	white-space: normal;
}

#resultsSectionDocs{
	visibility:hidden;
	width: 40%;
	padding: 2.5em;
	margin-top: 3em;
	margin-bottom: 3em;
	margin-left: 33%;
	background: #fff;
	background-color: white;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
	box-shadow: 10px 10px 5px #888888;
	border-radius: 10px 10px 10px 10px;
}

input#titleText{
	min-width:50%;
}

input#authorText{
	min-width:50%;
}

#tags{
  float:left;
  border:1px solid #ccc;
  padding:5px;
  font-family:Arial;
  width: 75%;
}

div.tagSearches{
	margin-left:15%;
}

div.titleFields{
	width:100%;
	margin-bottom:0.5em;
	display:inline-block;
	margin-left: 1.6em;
}

div.authorFields{
	width:100%;
	margin-bottom:0.5em;
	display:inline-block;
	margin-left: 0.5em;
}

div.tagFields{
	width:100%;
	margin-bottom:0.5em;
	display:inline-block;
	margin-left: 0.5em;
}

button#searchBook{
	min-width:40%;
}

button#resetSearch{
	min-width:40%;
	}

div.search-box{
	width: 40%;
	padding: 1em;
	margin-left:33%;
	margin-top:2em;
	overflow:hidden;
	background: #fff;
	background-color: #ededed;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
	box-shadow: 10px 10px 5px #888888;
	border-radius: 10px 10px 10px 10px;
   display: block;
   text-shadow: 0.5px 0.5px #a8a8a8;
}

button.accordion {
    background-color: #eee;
	background-image:url(../images/bgimg.jpg);
	
    color: #444;
    cursor: pointer;
    padding: 1em;
    width: 40%;
    text-align: left;
    border: none;
	margin-left:33%;
    outline: none;
    transition: 0.4s;
}

button.accordion:after {
    content: "\2796"; /* Unicode character for "plus" sign (+) */
    font-size: 13px;
    color: #777;
    float: right;
    margin-left: 5px;
}

.iconspanSearchDocs{
	 float: center;
    margin-right: 7px;
    margin-top: 5px;
    position: relative;
	background-color: #A2C5D0;
    color: #3D3C3A;
	border: solid 1.5px #dcdcdc;
	padding: 8px;
	border-left: none;
	border-radius: 0px 7px 7px 0px;
}

button.accordion.active:after {
    content: '\02795'; /* Unicode character for "minus" sign (-) */
}