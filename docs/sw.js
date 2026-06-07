const CACHE_NAME = 'opengraph-lite-viewer-v4388';

const ASSETS = [
  './',
  './index.html',
  './styles.css',
  './viewer.js',
  './examples-input.html',
  './favicon.ico',
  './manifest.webmanifest',
  './icons/icon-192.svg',
  './icons/icon-512.svg',
  './samples/no_limit_96_demo.json',
  './samples/short_demo.json',
  './samples/space3_gridH20W20_grow_demo.json',
  './carousel/index.html',
  './carousel/carousel.css',
  './carousel/carousel.js',
  './carousel/slides.json',
  './carousel/slides/01-simpele-vertakking-een-level.png',
  './carousel/slides/01-simpele-vertakking-een-level.txt',
  './carousel/slides/02-vertakking-links-rechts-varianten.png',
  './carousel/slides/02-vertakking-links-rechts-varianten.txt',
  './carousel/slides/03-oude-boom-s-np-vp.png',
  './carousel/slides/03-oude-boom-s-np-vp.txt',
  './carousel/slides/04-oude-boom-s-vp-np.png',
  './carousel/slides/04-oude-boom-s-vp-np.txt',
  './carousel/slides/05-standaard-m-tree.png',
  './carousel/slides/05-standaard-m-tree.txt',
  './carousel/slides/06-standaard-m-tree-variant.png',
  './carousel/slides/06-standaard-m-tree-variant.txt',
  './carousel/slides/07-projectieveld-klein.png',
  './carousel/slides/07-projectieveld-klein.txt',
  './carousel/slides/08-projectieveld-klein-variant.png',
  './carousel/slides/08-projectieveld-klein-variant.txt',
  './carousel/slides/09-kale-projectielijn-links.png',
  './carousel/slides/09-kale-projectielijn-links.txt',
  './carousel/slides/10-projectie-met-rand-boven.png',
  './carousel/slides/10-projectie-met-rand-boven.txt',
  './carousel/slides/11-projectie-met-rand-en-kopie.png',
  './carousel/slides/11-projectie-met-rand-en-kopie.txt',
  './carousel/slides/12-projectie-met-labelruimte.png',
  './carousel/slides/12-projectie-met-labelruimte.txt'
];

self.addEventListener('install', event => {
  event.waitUntil(caches.open(CACHE_NAME).then(cache => cache.addAll(ASSETS)));
  self.skipWaiting();
});

self.addEventListener('activate', event => {
  event.waitUntil(
    caches.keys().then(keys => Promise.all(keys.filter(key => key !== CACHE_NAME).map(key => caches.delete(key))))
  );
  self.clients.claim();
});

self.addEventListener('fetch', event => {
  if (event.request.method !== 'GET') return;
  event.respondWith(
    caches.match(event.request).then(cached => cached || fetch(event.request).then(response => {
      const copy = response.clone();
      caches.open(CACHE_NAME).then(cache => cache.put(event.request, copy));
      return response;
    }).catch(() => caches.match('./index.html')))
  );
});
