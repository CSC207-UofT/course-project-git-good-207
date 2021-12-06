import { Component, OnInit } from '@angular/core';

export interface Post {
  authorUsername: String;
  likeCount: number;
  comments: PostComment[];
  imageUrl: String;
  postDate: String;
  postIngredients: String[];
  postTitle: String,
  postSteps: String[],
  postId: String;
}

export interface PostComment {
  authorUsername: String;
  postDate: String;
  commentText: String;
}

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})

export class FeedComponent implements OnInit {
  public filters: any[] = [
    { value: 'Chinese' },
    { value: 'Italian' },
    { value: 'Japanese' },
    { value: 'Rwandan' },
    { value: 'Polish' },
    { value: 'American' },
    { value: 'Canadian' }
  ]

  public allPosts: Post[] = [
    {
      postId: '1',
      authorUsername: 'Eric',
      likeCount: 103933345,
      comments: [],
      postDate: '2:30pm Dec 1 2021',
      postTitle: 'Beef Wellington',
      postIngredients: ['1 pound beef tenderloin', '2 cups of salt', '5 tablespoons of special sauce'],
      postSteps: ['1. Buy Beef.', '2. Put the beef in the oven', '3. Enjoy your beef wellington.'],
      imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Inside_of_a_medium-rare_Beef_Wellington.jpg/1024px-Inside_of_a_medium-rare_Beef_Wellington.jpg'
    },
    {
      postId: '2',
      authorUsername: 'Shawn',
      likeCount: 52,
      comments: [],
      postDate: '1:23am Nov 30 2021',
      postIngredients: ['1 mililitre of vanilla ice cream'],
      postTitle: 'Ice  Cream Sundae',
      postSteps: ['1. Put your ice cream in the bowl.', '2. Put the ice cream in the oven.', '3. Enjoy your ice cream.'],
      imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg/800px-Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg'
    },
    {
      postId: '3',
      authorUsername: 'Yolanda',
      likeCount: 52,
      comments: [],
      postDate: '1:23am Nov 30 2021',
      postIngredients: [
        '1 cup Whipped Cream',
        '100 Sprinkles',
        '1.5 Oreos',
        '1 block of cheese',
        '1 Melba toast',
        '5 macaroons',
        '3 tablespoons wasabi',
        '1 white onion',
        '1 cup caramel, melted',
        '3.3674 grapes',
        '500 litres of water'
      ],
      postTitle: 'Flavoured Smoothie',
      postSteps: ['1. Put the water and ingredients in a blender.', '2. Blend for 30 seconds.'],
      imageUrl: 'https://content.instructables.com/ORIG/FCN/W0OZ/HIDCP3X8/FCNW0OZHIDCP3X8.jpg?auto=webp&frame=1&width=600&fit=bounds&md=73b53619b6b04d6cbc0742cdea7d2c65'
    },
  ];
  public filteredPosts: Post[] = [
    {
      postId: '1',
      authorUsername: 'Eric',
      likeCount: 103933345,
      comments: [],
      postDate: '2:30pm Dec 1 2021',
      postTitle: 'Beef Wellington',
      postIngredients: ['1 pound beef tenderloin', '2 cups of salt', '5 tablespoons of special sauce'],
      postSteps: ['1. Buy Beef.', '2. Put the beef in the oven', '3. Enjoy your beef wellington.'],
      imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Inside_of_a_medium-rare_Beef_Wellington.jpg/1024px-Inside_of_a_medium-rare_Beef_Wellington.jpg'
    },
    {
      postId: '2',
      authorUsername: 'Shawn',
      likeCount: 52,
      comments: [],
      postDate: '1:23am Nov 30 2021',
      postIngredients: ['1 mililitre of vanilla ice cream'],
      postTitle: 'Ice  Cream Sundae',
      postSteps: ['1. Put your ice cream in the bowl.', '2. Put the ice cream in the oven.', '3. Enjoy your ice cream.'],
      imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg/800px-Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg'
    },
    {
      postId: '3',
      authorUsername: 'Yolanda',
      likeCount: 52,
      comments: [],
      postDate: '1:23am Nov 30 2021',
      postIngredients: [
        '1 cup Whipped Cream',
        '100 Sprinkles',
        '1.5 Oreos',
        '1 block of cheese',
        '1 Melba toast',
        '5 macaroons',
        '3 tablespoons wasabi',
        '1 white onion',
        '1 cup caramel, melted',
        '3.3674 grapes',
        '500 litres of water'
      ],
      postTitle: 'Flavoured Smoothie',
      postSteps: ['1. Put the water and ingredients in a blender.', '2. Blend for 30 seconds.'],
      imageUrl: 'https://content.instructables.com/ORIG/FCN/W0OZ/HIDCP3X8/FCNW0OZHIDCP3X8.jpg?auto=webp&frame=1&width=600&fit=bounds&md=73b53619b6b04d6cbc0742cdea7d2c65'
    },
  ];

  constructor() { }

  ngOnInit(): void {
  }

  public likePost(feedPost: Post): void {
    // call API to update like/or update post
    for (let filteredPost of this.filteredPosts) {
      if (feedPost.postId === filteredPost.postId) {
        filteredPost.likeCount += 1;
      }
    }
  }
}
