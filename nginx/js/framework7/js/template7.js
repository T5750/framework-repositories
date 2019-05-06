var $$ = Dom7;
// Compile templates once on app load/init
var searchTemplate = $$('script#search-template').html();
var app = new Framework7({
	root: '#app',
	routes: [
		{
			path: '/tabs-routable/',
			template: searchTemplate,
			tabs: [
				{
					path: '/',
					id: 'tab1',
					content: ' \
          <div class="block"> \
            <p>Tab 1 content</p> \
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ullam enim quia molestiae facilis laudantium voluptates obcaecati officia cum, sit libero commodi. Ratione illo suscipit temporibus sequi iure ad laboriosam accusamus?</p> \
            <p>Saepe explicabo voluptas ducimus provident, doloremque quo totam molestias! Suscipit blanditiis eaque exercitationem praesentium reprehenderit, fuga accusamus possimus sed, sint facilis ratione quod, qui dignissimos voluptas! Aliquam rerum consequuntur deleniti.</p> \
            <p>Totam reprehenderit amet commodi ipsum nam provident doloremque possimus odio itaque, est animi culpa modi consequatur reiciendis corporis libero laudantium sed eveniet unde delectus a maiores nihil dolores? Natus, perferendis.</p> \
          </div> \
          '
				},
				{
					path: '/tab2/',
					id: 'tab2',
					content: '\
          <div class="block"> \
            <p>Tab 2 content</p> \
            <p>Suscipit, facere quasi atque totam. Repudiandae facilis at optio atque, rem nam, natus ratione cum enim voluptatem suscipit veniam! Repellat, est debitis. Modi nam mollitia explicabo, unde aliquid impedit! Adipisci!</p> \
            <p>Deserunt adipisci tempora asperiores, quo, nisi ex delectus vitae consectetur iste fugiat iusto dolorem autem. Itaque, ipsa voluptas, a assumenda rem, dolorum porro accusantium, officiis veniam nostrum cum cumque impedit.</p> \
            <p>Laborum illum ipsa voluptatibus possimus nesciunt ex consequatur rem, natus ad praesentium rerum libero consectetur temporibus cupiditate atque aspernatur, eaque provident eligendi quaerat ea soluta doloremque. Iure fugit, minima facere.</p> \
          </div> \
          '
				},
				{
					path: '/tab3/',
					id: 'tab3',
					content: '\
          <div class="block"> \
            <p>Tab 3 content</p> \
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ullam enim quia molestiae facilis laudantium voluptates obcaecati officia cum, sit libero commodi. Ratione illo suscipit temporibus sequi iure ad laboriosam accusamus?</p> \
            <p>Deserunt adipisci tempora asperiores, quo, nisi ex delectus vitae consectetur iste fugiat iusto dolorem autem. Itaque, ipsa voluptas, a assumenda rem, dolorum porro accusantium, officiis veniam nostrum cum cumque impedit.</p> \
            <p>Laborum illum ipsa voluptatibus possimus nesciunt ex consequatur rem, natus ad praesentium rerum libero consectetur temporibus cupiditate atque aspernatur, eaque provident eligendi quaerat ea soluta doloremque. Iure fugit, minima facere.</p> \
          </div> \
          '
				}
			]
		}
	]
});
var mainView = app.views.create('.view-main');
var compiledSearchTemplate = Template7.compile(searchTemplate);
// var listTemplate = $$('script#list-template').html();
// var compiledListTemplate = Template7.compile(listTemplate);
// That is all, now and further just execute compiled templates with required context
app.on('pageInit', function (page) {
	// Just execute compiled search template with required content:
	var html = compiledSearchTemplate({/*...some data...*/});
	// Do something with html...
});