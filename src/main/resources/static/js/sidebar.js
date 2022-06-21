$('#sidebar-collapse').click(function(e) {
    e.preventDefault();
    expandCollapseSidebar();
});

function expandCollapseSidebar(){
    $('.sidebar__txt').toggleClass('div__hidden');
    $('.navbar__txt').toggleClass('div__hidden');
    //$('.header-content').toggleClass('text-center');
    //$('.header-head').toggleClass('text-center');
    $('.side-icon').toggleClass('font__size20');
    $('.sidebar__main').toggleClass('sidebar__expand sidebar__collapse');
    $('.content__main').toggleClass('content__main-expand content__main-collapse');
    $('.navbar__main').toggleClass('navbar__main-expand navbar__main-collapse');
    $('.sidebar__a').toggleClass('txt__center');
}
