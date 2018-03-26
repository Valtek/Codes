using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Realtors.Data;
using Realtors.Models;

namespace Realtors.Controllers
{
    [Produces("application/json")]
    [Route("api/Realtor")]
    public class RealtorController : Controller
    {
        private ApplicationContext _context;

        public RealtorController(ApplicationContext context)
        {
            _context = context;
        }

        // GET: api/Realtor
        [HttpGet]
        public async Task<IActionResult> GetAllRealtors()
        {
            var result = await (from realtor in _context.Realtor
                                    join division in _context.Division
                                        on realtor.Division equals division.Id
                                    select new
                                    {
                                        id = realtor.Id,
                                        guid = realtor.Guid,
                                        lastName = realtor.LastName,
                                        firstName = realtor.FirstName,
                                        division = division.Name,
                                        registerDate = realtor.RegisterDate.ToString("dd.MM.yyyy")
                                    }).ToListAsync();
            return Ok(result);
        }

        // GET: api/Realtor/5
        [HttpGet("{id}", Name = "Get")]
        public async Task<IActionResult> GetRealtor(int id)
        {
            var realtor = await _context.Realtor.SingleOrDefaultAsync(x => x.Id == id);

            return Ok(realtor);
        }

        // POST: api/Realtor
        [HttpPost]
        public async Task<IActionResult> CreateRealtor([FromBody] Realtor realtor)
        {
            _context.Realtor.Add(realtor);
            await _context.SaveChangesAsync();
            return Ok();
        }

        // PUT: api/Realtor/5
        [HttpPut("{id}")]
        public async Task<IActionResult> UpdateRealtor(int id, [FromBody] Realtor updatedRealtor)
        {
            var realtor = await _context.Realtor.SingleOrDefaultAsync(x => x.Id == id);

            realtor.LastName = updatedRealtor.LastName;
            realtor.FirstName = updatedRealtor.FirstName;
            realtor.Division = updatedRealtor.Division;
            realtor.RegisterDate = updatedRealtor.RegisterDate;

            _context.Realtor.Update(realtor);
            await _context.SaveChangesAsync();
            return Ok();
        }

        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteRealtor(int id)
        {
            var realtor = await _context.Realtor.SingleOrDefaultAsync(x => x.Id == id);

            _context.Realtor.Remove(realtor);
            await _context.SaveChangesAsync();

            return Ok();
        }
    }
}
