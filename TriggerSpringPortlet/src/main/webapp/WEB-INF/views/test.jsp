<div class="four wide column center aligned votes">
	<div class="ui statistic">
		<div class="value">{{ votes }}</div>
		<div class="label">Points</div>
	</div>
</div>
<div class="twelve wide column">
	<a class="ui large header" href="{{ link }}"> {{ title }} </a>
	<ul class="ui big horizontal list voters">

		<li class="item">17 <a href (click)="voteUp()"> 18 <i
				class="arrow up icon"></i> upvote
		</a>
		</li>
		<li class="item"><a href (click)="voteDown()"> <i
				class="arrow down icon"></i> downvote
		</a> 
		Writing your First Angular 2 Web Application</li>
	</ul>
</div>
s