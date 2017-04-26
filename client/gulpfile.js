gulp=require('gulp'),
clean =require('gulp-clean'),
inject=require('gulp-inject'),
bowerfiles=require('main-bower-files'),
gulpfilter=require('gulp-filter'),
angularFileSort=require('gulp-angular-filesort'),
concat=require('gulp-concat'),
cleanCss=require('gulp-clean-css'),
cleanJS=require('gulp-uglify'),
merge=require('merge-stream'),
browserSync=require('browser-sync').create();

/**
 * Bower files will give us array of all the files that we installed by using
 npm install main-bower-files --save-dev. This is required because these are
 the entry points for all the dependencies we installed like bootstrap etc
 *
 */


var config={
  paths:{
      src:'./src',
      build:'./build',
      bower:'./bower_components'
  }
};

gulp.task('clean',function(){

return gulp.src(config.paths.build,{read:false}).pipe(clean());
});

gulp.task('inject',function(){
   var cssFiles=gulp.src([config.paths.src+'/**/*.css'],{read:false});

   var jsFiles=gulp.src([config.paths.src+'/**/*.js']);

   return gulp.src(config.paths.src+'/index.html').
   pipe(inject(gulp.src(bowerfiles(),{read:false}),{name:'bower'}))
       .pipe(inject(cssFiles,{
       ignorePath:'src',addRootSlash:false
   })).
   pipe(inject(jsFiles.pipe(angularFileSort()),{
       ignorePath:'src',addRootSlash:false
   })).pipe(gulp.dest(config.paths.build));

});
//Using inject because, before running this inject task should happen

gulp.task('serve',['inject'],function(){
    browserSync.init({
        server:{
            baseDir:[config.paths.build,config.paths.bower,config.paths.src],
            routes:{
             '/bower_components':'bower_components'
            }
        },
        files:[
            config.paths.src+'/**'
        ]

    })
});

//Minification task

gulp.task('minifyCss',function(){
var vendorStyles=gulp.src(bowerfiles()).pipe(gulpfilter(['**/*.css'])).
    pipe(concat('vendor.min.css')).
    pipe(cleanCss({debug:true})).
    pipe(gulp.dest(config.paths.build+'/styles'));

var appStyles=gulp.src(config.paths.src+'/**/*.css').
    pipe(concat('app.min.css')).
    pipe(cleanCss({debug:true})).
    pipe(gulp.dest(config.paths.build+'/styles'));

return merge(vendorStyles,appStyles);
});

gulp.task('minifyJS',function(){
    var vendorJS=gulp.src(bowerfiles()).pipe(gulpfilter(['**/*.js'])).
        pipe(concat('vendor.min.js')).
        pipe(cleanJS({debug:true})).
        pipe(gulp.dest(config.paths.build+'/scripts'));

var appJS=gulp.src(config.paths.src+'/**/*.js').
    pipe(angularFileSort()).
    pipe(concat('app.min.js')).
    pipe(cleanJS({debug:true})).
    pipe(gulp.dest(config.paths.build+'/scripts'));

return merge(vendorJS,appJS);


});


gulp.task('html',function(){
    return gulp.src([config.paths.src+'/**/*.html','!'+config.paths.src+'/index.html'])
        .pipe(gulp.dest(config.paths.build));

});

gulp.task('fonts',function(){
    return gulp.src(bowerfiles()).pipe(gulpfilter(['**/*.{svg,eot,tt,woff,woff2}'])).
        pipe(gulp.dest(config.paths.build+'/fonts'));
});

gulp.task('other',function(){
    return gulp.src([config.paths.src+'/**/*','!**/*.html','!**/*.css','!**/*.js']).
        pipe(gulp.dest(config.paths.build+'/other'));
});


gulp.task('build',['html','fonts','other','minifyJS','minifyCss'],function(){
    var vendorFiles=gulp.src([config.paths.build+'/styles/vendor.min.css',config.paths.build+'/scripts/vendor.min.js'],{read:false});
    var appFiles=gulp.src([config.paths.build+'/styles/app.min.css',config.paths.build+'/scripts/app.min.js'],{read:false});


    return gulp.src(config.paths.src+'/index.html').
        pipe(inject(vendorFiles ,{name:'vendor',ignorePath:'build',addRootSlash:false})).
        pipe(inject(appFiles,{name:'app',ignorePath:'build',addRootSlash:false})).
    pipe(gulp.dest(config.paths.build));

});