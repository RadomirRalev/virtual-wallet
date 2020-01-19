$(document).ready(function(){
  'use strict';

  //===== One Page Script =====//
  $('.one-page-scrol li a, .one-page-scrol2 li a').on('click', function(e) {
    e.preventDefault(); // prevent hard jump, the default behavior

    var target = $(this).attr('href'); // Set the target as variable

    // perform animated scrolling by getting top-position of target-element and set it as scroll target
    $('html, body').stop().animate({
      scrollTop: $(target).offset().top
    }, 600, function() {
      location.hash = target; //attach the hash (#jumptarget) to the pageurl
    });

    return false;
  });

  $(window).on('scroll',function() {
    var scrollDistance = $(window).scrollTop();
    $('.pg-scn,.pg-scn2').each(function(i) {
      if ($(this).position().top <= scrollDistance) {
        $('.one-page-scrol li.active, .one-page-scrol2 li.active').removeClass('active');
        $('.one-page-scrol li, .one-page-scrol2 li').eq(i).addClass('active');
      }
    });
  }).on('scroll');

  //===== Wow Animation Setting =====//
  var wow = new WOW({
    boxClass:     'wow',      // default
    animateClass: 'animated', // default
    offset:       0,          // default
    mobile:       true,       // default
    live:         true        // default
  }); 

  wow.init();

  //===== Dropdown Anmiation =====// 
  var drop = $('.scl-lnks > a');
  $('.scl-lnks').each(function(){
    var delay = 0;
    $(this).find(drop).each(function(){
      $(this).css({transitionDelay: delay+'s'});
      delay += .1;
    });
  }); 

  //===== Counter Up =====//
  if ($.isFunction($.fn.counterUp)) {
    $('.counter').counterUp({
      delay: 10,
      time: 2000
    });
  }

  //===== Accordion =====//
  $('#toggle .toggle-content').hide();
  $('#toggle h6:last').addClass('active').next().slideDown(500).parent().addClass('activate');
  $('#toggle h6').on('click', function() {
    if ($(this).next().is(':hidden')) {
      $('#toggle h6').removeClass('active').next().slideUp(500).parent().removeClass('activate');
      $(this).toggleClass('active').next().slideDown(500).parent().toggleClass('activate');
    }
  });

  //===== Accordion =====//
  $('#toggle2 .toggle-content').hide();
  $('#toggle2 h4:first').addClass('active').next().slideDown(500).parent().addClass('activate');
  $('#toggle2 h4').on('click', function() {
    if ($(this).next().is(':hidden')) {
      $('#toggle2 h4').removeClass('active').next().slideUp(500).parent().removeClass('activate');
      $(this).toggleClass('active').next().slideDown(500).parent().toggleClass('activate');
    }
  });

  //===== Responsive Header =====//
  $('.res-menu-btn').on('click', function () {
    $('.res-menu').addClass('slidein');
    return false;
  });
  $('.res-menu-close').on('click', function () {
    $('.res-menu').removeClass('slidein');
    return false;
  });
  $('.res-menu li.menu-item-has-children > i').on('click', function () {
    $(this).parent().siblings().children('ul').slideUp();
    $(this).parent().siblings().removeClass('active');
    $(this).parent().children('ul').slideToggle();
    $(this).parent().toggleClass('active');
    return false;
  });

  //===== Scrollbar =====//
  if ($('.res-menu').length) {
    var ps = new PerfectScrollbar('.res-menu');
  }

  //===== LightBox =====//
  if ($.isFunction($.fn.fancybox)) {
    $('[data-fancybox],[data-fancybox="gallery"]').fancybox({});
  }

  //===== Selectbox =====//
  if ($.isFunction($.fn.selectpicker)) {
    $('select').selectpicker();
  }

  //===== Count Down =====//
  if ($.isFunction($.fn.downCount)) {
    $('.countdown').downCount({
      date: '12/12/2019 12:00:00',
      offset: +5
    });
  }

  //===== Owl Carousel =====//
  if ($.isFunction($.fn.owlCarousel)) {

    //=== Priorities Carousel ===//
    $('.testimonials-carousel').owlCarousel({
      autoplay: true,
      smartSpeed: 600,
      loop: true,
      items: 1,
      dots: false,
      slideSpeed: 2000,
      autoplayHoverPause: true,
      nav: true,
      margin: 30,
      navText: [
      "<i class='fa fa-angle-left'></i>",
      "<i class='fa fa-angle-right'></i>"
      ]
    });

    //=== Global Carousel ===//
    $('.glb-car').owlCarousel({
      autoplay: true,
      smartSpeed: 600,
      loop: true,
      items: 4,
      dots: false,
      slideSpeed: 2000,
      autoplayHoverPause: true,
      nav: false,
      margin: 20,
      navText: [
      "<i class='fa fa-angle-left'></i>",
      "<i class='fa fa-angle-right'></i>"
      ],
      responsive:{
        0:{items: 1},
        480:{items: 2},
        768:{items: 3},
        980:{items: 3},
        1024:{items: 4},
        1200:{items: 4}
      }
    });

    //=== Tweets Carousel ===//
    $('.breaking-news-carousel').owlCarousel({
      autoplay: true,
      smartSpeed: 3000,
      loop: true,
      items: 1,
      dots: false,
      slideSpeed: 2000,
      autoplayHoverPause: true,
      nav: false,
      margin: 20,
      animateIn: 'slideInDown',
      animateOut: 'slideOutDown'
    });
    setTimeout( function(){
      jQuery('.breaking-news-carousel').css('display','block');
    },1000);

    //=== Related Posts Carousel ===//
    $('.releated-post-carousel').owlCarousel({
      autoplay: true,
      smartSpeed: 600,
      loop: true,
      items: 2,
      dots: true,
      slideSpeed: 2000,
      autoplayHoverPause: true,
      nav: false,
      margin: 30,
      responsive:{
        0:{items: 1},
        480:{items: 1},
        768:{items: 2},
        980:{items: 2},
        1024:{items: 2},
        1200:{items: 2}
      }
    });

  }

});//===== Document Ready Ends =====//


$(window).on('load',function(){
  'use strict';

  //===== Page Loader =====//
  jQuery('.pageloader-wrap').fadeOut('slow');

  var menu_height = $('header').innerHeight();
  if ($('header').hasClass('stick')) {
    $('main').css({'padding-top': menu_height});
  }

  //===== Isotope =====//
  if (jQuery('.filtr-cat').length > 0) {
    if (jQuery().isotope) {
      var jQuerycontainer = jQuery('.masonry'); // cache container
      jQuerycontainer.isotope({
        itemSelector: '.filtr-cat',
        columnWidth:.5,
      });
      jQuery('.filter-btns a').on('click',function () {
        var selector = jQuery(this).attr('data-filter');
        jQuery('.filter-btns li').removeClass('active');
        jQuery(this).parent().addClass('active');
        jQuerycontainer.isotope({filter: selector});
        return false;
      });
      jQuerycontainer.isotope('layout'); // layout/layout
    }

    jQuery(window).resize(function () {
      if (jQuery().isotope) {
        jQuery('.masonry').isotope('layout'); // layout/relayout on window resize
      }
    });
  }
  
  //===== Pageloader =====//
  $('.pageloader').fadeOut('slow');
});//===== Window onLoad Ends =====//

//===== Sticky Header =====//
$(window).on('scroll',function () {
  'use strict';

  var menu_height = $('header').innerHeight();
  var scroll = $(window).scrollTop();
  if (scroll >= menu_height) {
    $('.stick').addClass('sticky');
  } else {
    $('.stick').removeClass('sticky');
  }
});//===== Window onScroll Ends =====//
